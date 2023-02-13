package com.ed.app.linesdkmongodbintegrationdemo.controller;

import com.ed.app.linesdkmongodbintegrationdemo.common.exception.UnknownOriginRequest;
import com.ed.app.linesdkmongodbintegrationdemo.common.exception.VerifyOriginFailedException;
import com.ed.app.linesdkmongodbintegrationdemo.model.dto.webhook.ReplyMessageRequestDto;
import com.ed.app.linesdkmongodbintegrationdemo.model.dto.webhook.ReplyMessageResponseDto;
import com.ed.app.linesdkmongodbintegrationdemo.service.WebhookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static com.ed.app.linesdkmongodbintegrationdemo.common.constant.AppConstant.LINE_ALGORITHM;
import static com.ed.app.linesdkmongodbintegrationdemo.common.constant.AppConstant.LINE_CHANNEL_SECRET;

@RestController
@Slf4j
@RequestMapping(value = "/webhook")
public record WebhookController(WebhookService webhookService) {
    public static final String REPLY_MESSAGE_URI = "api/v1/reply-message";
    public static final String RECEIVE_MESSAGE_URI = "api/v1/receive";

    /**
     * <pre>
     * 處理來至 Webhook event的請求，確認來源為 Line後，將委派 webhookService處理
     *
     * @param xLineSignature - The signature in the x-line-signature request header
     * @param requestBody - 來至 Webhook's requestBody
     * @return - ResponseEntity
     * @throws InvalidKeyException - 若是 signature不正確將會拋出此錯
     * @throws UnknownOriginRequest - 若是來源不是 Line將會拋出此錯
     *
     * </pre>
     */
    @PostMapping(value = RECEIVE_MESSAGE_URI)
    public ResponseEntity<?> receiveMessage(
            @RequestHeader("X-Line-Signature") String xLineSignature,
            @RequestBody String requestBody) throws InvalidKeyException, UnknownOriginRequest {
        log.info("receive request from webhook event - xLineSignature: {}, requestBody: {}",
                xLineSignature, requestBody);
        checkRequestFromLine(requestBody, xLineSignature);
        webhookService.processSaveWebhookEvent(requestBody);

        return ResponseEntity.ok().build();
    }

    /**
     * <pre>
     * 依據 request裡的 replyToken與 replyMessage回復訊息
     *
     * @param request - reply message's request
     * @return - ResponseEntity
     */
    @PostMapping(value = REPLY_MESSAGE_URI)
    public ResponseEntity<ReplyMessageResponseDto> replyMessage(@RequestBody ReplyMessageRequestDto request) {
        log.info("receive request: {}", request);
        var botApiResponse = webhookService.processReplyMessage(request);
        var response = ReplyMessageResponseDto.builder()
                .botApiResponse(botApiResponse)
                .build();

        return ResponseEntity.ok(response);
    }


    /**
     * <pre>
     * 確認來源是否為 Line，若不是將會丟錯
     *
     * @param requestBody - 來至 Webhook's requestBody
     * @param xLineSignature - The signature in the x-line-signature request header
     * @throws InvalidKeyException - 若是 signature不正確將會拋出此錯
     * @throws UnknownOriginRequest - 若是來源不是 Line將會拋出此錯
     *
     * </pre>
     */
    public void checkRequestFromLine(String requestBody, String xLineSignature)
            throws UnknownOriginRequest, InvalidKeyException {
        SecretKeySpec key = new SecretKeySpec(LINE_CHANNEL_SECRET.getBytes(), LINE_ALGORITHM);
        Mac mac;
        try {
            mac = Mac.getInstance(LINE_ALGORITHM);
            mac.init(key);
            var source = requestBody.getBytes(StandardCharsets.UTF_8);
            var signature = Base64.encodeBase64String(mac.doFinal(source));
            var isXLineSignaturePresent = Optional.ofNullable(xLineSignature).isPresent();
            var isRequestFromLine = isXLineSignaturePresent && signature.equals(xLineSignature);
            if(!isRequestFromLine)
                throw new UnknownOriginRequest();
        } catch (NoSuchAlgorithmException e) {
            log.error("e.getCause(): {}, e.getMessage(): {}", e.getCause(), e.getMessage());

            throw new VerifyOriginFailedException(e.getMessage());
        }
    }
}
