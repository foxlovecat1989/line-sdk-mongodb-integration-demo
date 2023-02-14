package com.ed.app.linesdkmongodbintegrationdemo.service.impl;

import com.ed.app.linesdkmongodbintegrationdemo.common.exception.DataNotFoundException;
import com.ed.app.linesdkmongodbintegrationdemo.common.util.BeanTransformUtil;
import com.ed.app.linesdkmongodbintegrationdemo.entity.po.MessagePo;
import com.ed.app.linesdkmongodbintegrationdemo.model.dto.message.QueryMessageRequestDto;
import com.ed.app.linesdkmongodbintegrationdemo.model.pojo.MessagePojo;
import com.ed.app.linesdkmongodbintegrationdemo.repository.MessageRepository;
import com.ed.app.linesdkmongodbintegrationdemo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public record MessageServiceImpl(MessageRepository messageRepository) implements MessageService {

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
     */
    @Override
    public void saveAllMessages(List<MessagePojo> messagePojos) {
        var messagePos = messagePojos.stream()
                .map(messagePojo -> (MessagePo) BeanTransformUtil.transformPojo2Po(messagePojo, MessagePo.class))
                .toList();
        var returnPos = messageRepository.saveAll(messagePos);
        log.info("Save the following Messages to DB: ");
        returnPos.forEach(returnPo -> log.info("From UserId: <{}>, Message: [{}], At: {}, ReplyToken: {}",
                returnPo.getUserId(), returnPo.getMessage(), returnPo.getCreateAt(), returnPo.getReplyToken()));
    }

    /**
     * <pre>
     * 透過 replyToken查找到符合的唯一 MessagePo結果，並轉換成 MessagePojo轉換成回應
     * 若無結果回覆 Optional.empty()
     *
     * @param replyToken - 回覆的 replyToken
     * @return - 透過 replyToken查找到的結果
     *
     * </pre>
     */
    @Override
    public Optional<MessagePojo> findByReplyToken(String replyToken) {
        var messagePoOpt =  messageRepository.findByReplyToken(replyToken);
        log.debug("By replyToken: {} - find messagePoOpt: {}", replyToken, messagePoOpt);

        return messagePoOpt.map(messagePo -> BeanTransformUtil.transformPo2Pojo(messagePo, MessagePojo.class));
    }

    /**
     * <pre>
     * 將此 id的 MessagePo's isHasBeenReplied設為 true
     *
     * @param id - MessagePo's id
     *
     * </pre>
     */
    @Override
    public void setIsHasBeenRepliedTrue(String id) {
        var messagePo = messageRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("id: " + id));
        messagePo.setIsHasBeenReplied(Boolean.TRUE);
        messageRepository.save(messagePo);
        log.info("set ID: <{}>'s MessagePo 已被回覆", id);
    }

    /**
     * <pre>
     * 透過 userId查詢 複數 MessagePojo結果
     *
     * @param request - 來至 controller的請求
     * @return - 複數 MessagePojo查詢結果
     * </pre>
     */
    @Override
    public List<MessagePojo> processQueryMessages(QueryMessageRequestDto request) {
        var messagePojos = findAllByUserId(request.getUserId());
        messagePojos.forEach(messagePojo ->
                log.info("Find UserId: <{}>, Message: [{}], CreateAt: {}, ReplyToken: {}",
                        messagePojo.getUserId(),
                        messagePojo.getMessage(),
                        messagePojo.getCreateAt(),
                        messagePojo.getReplyToken()));

        return messagePojos;
    }
}
