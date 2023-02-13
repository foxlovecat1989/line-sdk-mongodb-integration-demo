package com.ed.app.linesdkmongodbintegrationdemo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum WebhookEventType {
    FOLLOW("follow", ""),
    UNFOLLOW("unfollow", ""),
    MESSAGE("message", ""),
    UNKNOWN("unknown", "");

    private final String key;
    private final String description;

    public static WebhookEventType getEnum(String key) {
        return Arrays.stream(WebhookEventType.values())
                .filter(value -> StringUtils.equalsAnyIgnoreCase(value.getKey(), key))
                .findAny().orElse(UNKNOWN);
    }
}
