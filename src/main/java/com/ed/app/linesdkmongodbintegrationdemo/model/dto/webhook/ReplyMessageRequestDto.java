package com.ed.app.linesdkmongodbintegrationdemo.model.dto.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyMessageRequestDto {
    // 回覆的 token
    @JsonProperty(value = "replyToken")
    private String replyToken;
    // 回覆的訊息
    @JsonProperty(value = "replyMessage")
    private String replyMessage;
}
