package com.ed.app.linesdkmongodbintegrationdemo.service;

import com.ed.app.linesdkmongodbintegrationdemo.model.pojo.MessagePojo;

import java.util.List;

public interface MessageService {
    MessagePojo saveMessage(MessagePojo messagePojo);
    List<MessagePojo> findAllByUserId(String userId);
    List<MessagePojo> saveAllMessages(List<MessagePojo> messagePojos);
}
