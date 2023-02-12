package com.ed.app.linesdkmongodbintegrationdemo.model.pojo;

import com.ed.app.linesdkmongodbintegrationdemo.common.model.pojo.BasePojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagePojo implements BasePojo {
    @Id
    // 自動編號
    private String id;
    // 來自 LineBot的userId
    @Field(value = "userId")
    private String userId;
    // 訊息內容
    @Field(value = "message")
    private String message;
    // 建立時間
    @Field(value = "createAt")
    private Date createAt;
}
