package com.ed.app.linesdkmongodbintegrationdemo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MessageType {
    TEXT("text", "Message object which contains the text sent from the source"),
    IMAGE("image", "Message object which contains the image content sent from the source"),
    VIDEO("video", "Message object which contains the video content sent from the source"),
    AUDIO("audio", "Message object which contains the audio content sent from the source"),
    FILE("file", "Message object which contains the file sent from the source"),
    LOCATION("location", "Message object which contains the location data sent from the source"),
    STICKER("sticker", "Message object which contains the sticker data sent from the source"),
    UNKNOWN("unknown", "unknown");

    private final String key;
    private final String description;

    public static MessageType getEnum(String key) {
        return Arrays.stream(MessageType.values())
                .filter(value -> StringUtils.equalsAnyIgnoreCase(value.getKey(), key))
                .findAny().orElse(UNKNOWN);
    }
}
