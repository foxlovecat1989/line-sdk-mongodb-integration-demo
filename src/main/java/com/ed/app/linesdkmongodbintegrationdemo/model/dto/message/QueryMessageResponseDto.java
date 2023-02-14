package com.ed.app.linesdkmongodbintegrationdemo.model.dto.message;

import com.ed.app.linesdkmongodbintegrationdemo.model.pojo.MessagePojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class QueryMessageResponseDto {
    // 複數 MessagePojo查詢結果
    private List<MessagePojo> messagePojos;
}
