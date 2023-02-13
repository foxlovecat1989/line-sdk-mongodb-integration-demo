package com.ed.app.linesdkmongodbintegrationdemo.model.dto.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.linecorp.bot.model.response.BotApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyMessageResponseDto {
    // call line's reply message API response
    @JsonProperty(value = "botApiResponse")
    private BotApiResponse botApiResponse;
}
