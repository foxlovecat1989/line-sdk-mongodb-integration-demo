package com.ed.app.linesdkmongodbintegrationdemo.service;

import com.ed.app.linesdkmongodbintegrationdemo.model.pojo.MessagePojo;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    MessagePojo saveMessage(MessagePojo messagePojo);
    List<MessagePojo> findAllByUserId(String userId);
    List<MessagePojo> saveAllMessages(List<MessagePojo> messagePojos);
    Optional<MessagePojo> findByReplyToken(String replyToken);
    void setIsHasBeenRepliedTrue(String id);
}
