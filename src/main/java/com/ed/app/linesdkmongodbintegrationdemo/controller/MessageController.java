package com.ed.app.linesdkmongodbintegrationdemo.controller;

import com.ed.app.linesdkmongodbintegrationdemo.model.dto.message.QueryMessageRequestDto;
import com.ed.app.linesdkmongodbintegrationdemo.model.dto.message.QueryMessageResponseDto;
import com.ed.app.linesdkmongodbintegrationdemo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/message")
@Slf4j
public record MessageController(MessageService messageService) {
    public static final String QUERY_MESSAGES_URI = "api/v1/query-messages";

    /**
     * <pre>
     * 處理透過 userId查詢 複數 MessagePojo的請求
     *
     * @param request - 請求
     * @return - ResponseEntity - 複數 MessagePojo查詢結果
     *
     * </pre>
     */
    @PostMapping(value = QUERY_MESSAGES_URI)
    public ResponseEntity<QueryMessageResponseDto> queryMessages(@RequestBody QueryMessageRequestDto request) {
        log.info("receive request: {}", request);
        var messagePojos = messageService.processQueryMessages(request);
        var response = QueryMessageResponseDto.builder()
                .messagePojos(messagePojos)
                .build();

        return ResponseEntity.ok(response);
    }
}
