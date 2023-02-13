package com.ed.app.linesdkmongodbintegrationdemo.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConstant {
    // Line's channel secret
    public static String LINE_CHANNEL_SECRET;
    // Line's channel access token
    public static String LINE_CHANNEL_TOKEN;
    // algorithm of handling Line's signature
    public static String LINE_ALGORITHM;
    // 回復訊息前墜
    public static String REPLY_MESSAGE_PREFIX;

    @Value("${line.bot.channel-secret}")
    public void setLineChannelSecret(String lineChannelSecret) {
        LINE_CHANNEL_SECRET = lineChannelSecret;
    }

    @Value("${line.bot.channel-token}")
    public void setLineChannelToken(String lineChannelToken) {
        LINE_CHANNEL_TOKEN = lineChannelToken;
    }

    @Value("${line.bot.algorithm}")
    public void setLineAlgorithm(String algorithm) {
        LINE_ALGORITHM = algorithm;
    }

    @Value("${line.bot.reply-message-prefix}")
    public void setReplyMessagePrefix(String replyMessagePrefix) {
        REPLY_MESSAGE_PREFIX = replyMessagePrefix;
    }
}
