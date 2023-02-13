package com.ed.app.linesdkmongodbintegrationdemo.common.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record JsonUtil(ObjectMapper objectMapper) {

    /**
     * <pre>
     * 將 JSON格式的 String轉換至參數 clazz指定的型別，具有 Properties值
     *
     * @param jsonString - JSON格式的 String
     * @param clazz - 要轉換至的型別
     * @return - 轉換後的物件具有 Properties值
     * @throws JsonProcessingException - 若是JSON轉換時發生錯誤將會拋出此錯誤
     *
     * </pre>
     */
    public <T> T jsonToObject(String jsonString, Class<T> clazz) throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper.readValue(jsonString, clazz);
    }

    /**
     * <pre>
     * 將物件轉換至 JSON格式的 String
     *
     * @param obj - 要轉換的物件
     * @return - JSON格式的 String
     * @throws JsonProcessingException - 若是JSON轉換時發生錯誤將會拋出此錯誤
     *
     * </pre>
     */
    public String objectToJson(Object obj) throws JsonProcessingException {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        return objectMapper.writeValueAsString(obj);
    }
}
