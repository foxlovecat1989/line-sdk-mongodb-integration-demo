package com.ed.app.linesdkmongodbintegrationdemo.service;

import com.ed.app.linesdkmongodbintegrationdemo.model.dto.message.QueryMessageRequestDto;
import com.ed.app.linesdkmongodbintegrationdemo.model.pojo.MessagePojo;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<MessagePojo> findAllByUserId(String userId);
    void saveAllMessages(List<MessagePojo> messagePojos);
    Optional<MessagePojo> findByReplyToken(String replyToken);
    List<MessagePojo> processQueryMessages(QueryMessageRequestDto request);
    void setIsHasBeenRepliedTrue(String id);
}
