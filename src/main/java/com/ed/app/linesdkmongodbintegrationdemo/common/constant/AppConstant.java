package com.ed.app.linesdkmongodbintegrationdemo.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConstant {
    // Line's channel secret
    public static String LINE_CHANNEL_SECRET;
    // algorithm of handling Line's signature
    public static String LINE_ALGORITHM;

    @Value("${line.bot.channel-secret}")
    public void setLineChannelSecret(String lineChannelSecret) {
        LINE_CHANNEL_SECRET = lineChannelSecret;
    }

    @Value("${line.bot.algorithm}")
    public void setLineAlgorithm(String algorithm) {
        LINE_ALGORITHM = algorithm;
    }
}
