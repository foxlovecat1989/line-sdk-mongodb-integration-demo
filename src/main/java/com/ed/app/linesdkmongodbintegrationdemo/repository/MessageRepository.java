package com.ed.app.linesdkmongodbintegrationdemo.repository;

import com.ed.app.linesdkmongodbintegrationdemo.entity.po.MessagePo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<MessagePo, String> {
    List<MessagePo> findAllByUserId(String userId);
}
