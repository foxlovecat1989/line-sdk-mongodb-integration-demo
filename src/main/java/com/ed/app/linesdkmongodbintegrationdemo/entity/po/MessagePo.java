package com.ed.app.linesdkmongodbintegrationdemo.entity.po;

import com.ed.app.linesdkmongodbintegrationdemo.common.entity.po.BasePo;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagePo implements BasePo {
    // 自動編號
    @Id
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
