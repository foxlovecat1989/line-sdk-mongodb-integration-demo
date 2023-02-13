package com.ed.app.linesdkmongodbintegrationdemo.service.impl;

import com.ed.app.linesdkmongodbintegrationdemo.common.exception.CallLineApiException;
import com.ed.app.linesdkmongodbintegrationdemo.common.exception.DataNotFoundException;
import com.ed.app.linesdkmongodbintegrationdemo.common.exception.InvalidReplyTokenException;
import com.ed.app.linesdkmongodbintegrationdemo.common.exception.JsonFormattedException;
import com.ed.app.linesdkmongodbintegrationdemo.common.util.DateTimeUtil;
import com.ed.app.linesdkmongodbintegrationdemo.common.util.JsonUtil;
import com.ed.app.linesdkmongodbintegrationdemo.model.dto.webhook.ReplyMessageRequestDto;
import com.ed.app.linesdkmongodbintegrationdemo.model.dto.webhook.WebhookEventRequestDto;
import com.ed.app.linesdkmongodbintegrationdemo.model.enums.MessageType;
import com.ed.app.linesdkmongodbintegrationdemo.model.enums.WebhookEventType;
import com.ed.app.linesdkmongodbintegrationdemo.model.pojo.MessagePojo;
import com.ed.app.linesdkmongodbintegrationdemo.service.MessageService;
import com.ed.app.linesdkmongodbintegrationdemo.service.WebhookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

import static com.ed.app.linesdkmongodbintegrationdemo.common.constant.AppConstant.LINE_CHANNEL_TOKEN;
import static com.ed.app.linesdkmongodbintegrationdemo.common.constant.AppConstant.REPLY_MESSAGE_PREFIX;

@Service
@Slf4j
public record WebhookServiceImpl(MessageService messageService, JsonUtil jsonUtil) implements WebhookService {

    /**
     * <pre>
     * 處理來至 Webhook's event將需要的資訊(只取 WebhookEventType為 MESSAGE且 MessageType為 TEXT的 event)
     * 轉存 MessagePojo後存入 DB
     *
     * @param requestBody - 來至 webhook event's request body
     * @throws JsonFormattedException - 若是 requestBody轉換成 DTO時發生錯誤將會拋出此錯
     * </pre>
     */
    @Override
    public void processSaveWebhookEvent(String requestBody) {
        try {
            var requestDto = jsonUtil.jsonToObject(requestBody, WebhookEventRequestDto.class);

            Predicate<WebhookEventRequestDto.Event> isEventEqualsMessage =
                    event -> WebhookEventType.getEnum(event.getType()).equals(WebhookEventType.MESSAGE);
            Predicate<WebhookEventRequestDto.Event> isMessageTypeEqualsText =
                    event -> MessageType.getEnum(event.getMessage().getType()).equals(MessageType.TEXT);

            var messagePojos = requestDto.getEvents().stream()
                    .filter(isEventEqualsMessage)
                    .filter(isMessageTypeEqualsText)
                    .map(event -> MessagePojo.builder()
                            .userId(requestDto.getDestination())
                            .message(event.getMessage().getText())
                            .replyToken(event.getReplyToken())
                            .createAt(new Date(event.getTimestamp()))
                            .isHasBeenReplied(false)
                            .build())
                    .toList();

            messageService.saveAllMessages(messagePojos);
        } catch (JsonProcessingException e) {
            log.error(JsonFormattedException.ERROR_MSG);

            throw new JsonFormattedException();
        }
    }

    /**
     * <pre>
     * 處理來至 Controller's request 進行回覆訊息，需先檢驗 replyToken是否還是有效
     * 完全回覆訊息後，將 isHasBeenReplied 設為 true
     *
     * @param request - 來至 controller's request
     * @exception DataNotFoundException - 當使用 replyToken查找不到 MessagePojo時拋錯
     *
     * </pre>
     */
    @Override
    public BotApiResponse processReplyMessage(ReplyMessageRequestDto request) {
        var replyToken = request.getReplyToken();
        var messagePojo = messageService.findByReplyToken(replyToken)
                .orElseThrow(() -> {
                    var extendMessage =
                            String.format("MessagePojo Not Found By replyToken: %s", replyToken);

                    throw new DataNotFoundException(extendMessage);
                });
        checkIsValidReplyToken(messagePojo);
        final ReplyMessage replyMessage = getReplyMessage(messagePojo, request.getReplyMessage());
        var response = callApi(replyMessage);
        log.info("response: {}", response);
        messageService.setIsHasBeenRepliedTrue(messagePojo.getId());

        return response;
    }

    /**
     * <pre>
     * 取得 ReplyMessage物件，若是 replyMessage為空或空字串，則回覆同樣訊息給使用者
     *
     * @param messagePojo - messagePojo物件
     * @param replyMessage - 欲回覆的訊息
     * @return - new ReplyMessage物件
     *
     * </pre>
     */
    private ReplyMessage getReplyMessage(MessagePojo messagePojo, String replyMessage) {
        var isNotBlank = StringUtils.isNotBlank(replyMessage);
        var finalReplyMessage = isNotBlank ? replyMessage : REPLY_MESSAGE_PREFIX + messagePojo.getMessage();
        final TextMessage textMessage = new TextMessage(finalReplyMessage);

        return new ReplyMessage(messagePojo.getReplyToken(), textMessage);

    }

    /**
     * <pre>
     * 檢測是否已回覆過訊息或是已超過 20分鐘未回覆
     *
     * @param messagePojo - 檢測的 messagePojo物件
     * @throws InvalidReplyTokenException - 若是已回覆過訊息或是已超過 20分鐘未回覆，將會拋出此錯
     *
     * </pre>
     */
    private void checkIsValidReplyToken(MessagePojo messagePojo) {
        // The reply token included in the original webhook has already been used
        boolean isHasBeenReplied = messagePojo.getIsHasBeenReplied();
        var createAt = DateTimeUtil.date2LocalDateTime(messagePojo.getCreateAt());
        var now = LocalDateTime.now(ZoneId.systemDefault());
        // 20 minutes have passed since the event occurred
        boolean isExcessTwentyMins = createAt.minusMinutes(20L).isAfter(now);
        if (isHasBeenReplied || isExcessTwentyMins)
            throw new InvalidReplyTokenException();
    }

    /**
     * <pre>
     * 取得 LineMessagingClient物件
     *
     * @param replyMessage - 欲回覆的訊息
     * @return - 來至 Line API回覆的BotApiResponse物件
     *
     * </pre>
     */
    private BotApiResponse callApi(ReplyMessage replyMessage) {
        final LineMessagingClient client = getLineMessagingClient();
        try {
            return client.replyMessage(replyMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();

            throw new CallLineApiException(e.getMessage());
        }
    }

    /**
     * <pre>
     * 取得 LineMessagingClient物件
     *
     * @return - LineMessagingClient物件
     *
     * </pre>
     */
    private LineMessagingClient getLineMessagingClient() {
        return LineMessagingClient.builder(LINE_CHANNEL_TOKEN)
                .build();
    }
}
