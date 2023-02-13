package com.ed.app.linesdkmongodbintegrationdemo.model.pojo;

import com.ed.app.linesdkmongodbintegrationdemo.common.model.pojo.BasePojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagePojo implements BasePojo {
    // 自動編號
    @JsonProperty(value = "id")
    private String id;
    // 來自 LineBot的userId
    @JsonProperty(value = "userId")
    private String userId;
    // 訊息內容
    @JsonProperty(value = "message")
    private String message;
    // Reply token used to send reply message to this event
    @JsonProperty(value = "replyToken")
    private String replyToken;
    // 建立時間
    @JsonProperty(value = "createAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;
    // 是否已回復過訊息
    @JsonProperty(value = "isHasBeenReplied")
    private Boolean isHasBeenReplied;
}
