package com.linguochao.swagger.repository;

import com.linguochao.swagger.model.Message;

import java.util.List;

/**
 * description
 *
 * @author linguochao
 * @date 2019\7\13 0013
 */
public interface MessageRepository {

    List<Message> findAll();

    Message save(Message message);

    Message update(Message message);

    Message updateText(Message message);

    Message findMessage(Long id);

    void deleteMessage(Long id);

}
