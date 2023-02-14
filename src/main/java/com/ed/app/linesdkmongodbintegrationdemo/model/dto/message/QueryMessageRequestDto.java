package com.ed.app.linesdkmongodbintegrationdemo.model.dto.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryMessageRequestDto {
    // 使用者 ID
    private String userId;
}
