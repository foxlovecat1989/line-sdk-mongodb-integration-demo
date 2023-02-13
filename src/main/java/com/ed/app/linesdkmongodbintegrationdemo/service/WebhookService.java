package com.ed.app.linesdkmongodbintegrationdemo.service;

import com.ed.app.linesdkmongodbintegrationdemo.model.dto.webhook.ReplyMessageRequestDto;
import com.linecorp.bot.model.response.BotApiResponse;

public interface WebhookService {
    void processSaveWebhookEvent(String requestBody);
    BotApiResponse processReplyMessage(ReplyMessageRequestDto request);
}
