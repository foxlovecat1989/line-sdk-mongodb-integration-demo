package com.ed.app.linesdkmongodbintegrationdemo.service.impl;

import com.ed.app.linesdkmongodbintegrationdemo.common.util.BeanTransformUtil;
import com.ed.app.linesdkmongodbintegrationdemo.entity.po.MessagePo;
import com.ed.app.linesdkmongodbintegrationdemo.model.pojo.MessagePojo;
import com.ed.app.linesdkmongodbintegrationdemo.repository.MessageRepository;
import com.ed.app.linesdkmongodbintegrationdemo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public record MessageServiceImpl(MessageRepository messageRepository) implements MessageService {

    /**
     * <pre>
     * 將 messagePojo儲存至 DB，並 return來至 DB儲存後的 MessagePojo
     *
     * @param messagePojo - 要儲存至DB的 MessagePojo
     * @return - 來至 DB儲存後的 MessagePojo
     *
     * </pre>
     */
    @Override
    public MessagePojo saveMessage(MessagePojo messagePojo) {
        MessagePo messagePo = BeanTransformUtil.transformPojo2Po(messagePojo, MessagePo.class);
        MessagePo returnPo = messageRepository.save(messagePo);
        log.info("Save Message to DB - UserId: <{}>, Message: [{}], At: {}",
                returnPo.getUserId(), returnPo.getMessage(), returnPo.getCreateAt());

        return BeanTransformUtil.transformPo2Pojo(returnPo, MessagePojo.class);
    }

    /**
     * <pre>
     * 透過 userId查詢 DB所符合的複數 MessagePojo結果
     *
     * @param userId - 查詢條件: LineBot的 userId
     * @return - DB所符合的複數 MessagePojo結果
     *
     * </pre>
     */
    @Override
    public List<MessagePojo> findAllByUserId(String userId) {
        var messagePos = messageRepository.findAllByUserId(userId);
        log.info("Query by UserId: {} From DB: ", userId);
        var messagePojos = messagePos.stream()
                .map(messagePo -> (MessagePojo) BeanTransformUtil.transformPo2Pojo(messagePo, MessagePojo.class))
                .toList();
        messagePos.forEach(messagePojo -> log.info("messagePojo: {}", messagePojo));

        return messagePojos;
    }

    /**
     * <pre>
     * 將 messagePojos儲存至 DB，並 return來至 DB儲存後的 MessagePojos
     *
     * @param messagePojos - 要儲存至DB的 MessagePojos
     * @return - 來至 DB儲存後的 MessagePojos
     *
     * </pre>
     */
    @Override
    public List<MessagePojo> saveAllMessages(List<MessagePojo> messagePojos) {
        var messagePos = messagePojos.stream()
                .map(messagePojo -> (MessagePo) BeanTransformUtil.transformPojo2Po(messagePojo, MessagePo.class))
                .toList();
        var returnPos = messageRepository.saveAll(messagePos);
        log.info("Save the following Messages to DB: ");
        returnPos.forEach(returnPo -> log.info("From UserId: <{}>, Message: [{}], At: {}",
                returnPo.getUserId(), returnPo.getMessage(), returnPo.getCreateAt()));

        return returnPos.stream()
                .map(messagePo -> (MessagePojo) BeanTransformUtil.transformPo2Pojo(messagePo, MessagePojo.class))
                .toList();
    }
}
