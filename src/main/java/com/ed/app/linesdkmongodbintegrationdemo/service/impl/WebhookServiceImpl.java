package com.ed.app.linesdkmongodbintegrationdemo.service.impl;

import com.ed.app.linesdkmongodbintegrationdemo.common.exception.JsonFormattedException;
import com.ed.app.linesdkmongodbintegrationdemo.common.util.JsonUtil;
import com.ed.app.linesdkmongodbintegrationdemo.model.dto.webhook.WebhookEventRequestDto;
import com.ed.app.linesdkmongodbintegrationdemo.model.enums.MessageType;
import com.ed.app.linesdkmongodbintegrationdemo.model.enums.WebhookEventType;
import com.ed.app.linesdkmongodbintegrationdemo.model.pojo.MessagePojo;
import com.ed.app.linesdkmongodbintegrationdemo.service.MessageService;
import com.ed.app.linesdkmongodbintegrationdemo.service.WebhookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Predicate;

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
                            .createAt(new Date(event.getTimestamp()))
                            .build())
                    .toList();
            messageService.saveAllMessages(messagePojos);
        } catch (JsonProcessingException e) {
            log.error(JsonFormattedException.ERROR_MSG);

            throw new JsonFormattedException();
        }
    }
}
