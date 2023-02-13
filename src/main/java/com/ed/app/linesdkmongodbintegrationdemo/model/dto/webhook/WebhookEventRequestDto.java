package com.ed.app.linesdkmongodbintegrationdemo.model.dto.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookEventRequestDto {
    // User ID of a bot that should receive webhook events
    @JsonProperty(value = "destination")
    private String destination;
    // Array of webhook event objects
    @JsonProperty(value = "events")
    private List<Event> events;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Event {
        // Identifier for the type of event
        @JsonProperty(value = "type")
        private String type;
        // Webhook event object which contains the sent message.
        @JsonProperty(value = "message")
        private Message message;
        // Time of the event in milliseconds
        @JsonProperty(value = "timestamp")
        private Long timestamp;
        // Source user, group chat, or multi-person chat object with information about the source of the event
        @JsonProperty(value = "source")
        private Source source;
        // Reply token used to send reply message to this event
        @JsonProperty(value = "replyToken")
        private String replyToken;
        // Webhook Event ID. An ID that uniquely identifies a webhook event. This is a string in ULID format.
        @JsonProperty(value = "webhookEventId")
        private String webhookEventId;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Message {
            // Message type
            @JsonProperty(value = "type")
            private String type;
            // content
            @JsonProperty(value = "text")
            private String text;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Source {
            // ID of the source user
            @JsonProperty(value = "userId")
            private String userId;
        }
    }
}
