package com.hoctuan.codingforum.service.chat.impl;

import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.model.dto.chat.MessageRequestDTO;
import com.hoctuan.codingforum.model.dto.chat.MessageResponseDTO;
import com.hoctuan.codingforum.model.entity.chat.Message;
import com.hoctuan.codingforum.model.mapper.MessageMapper;
import com.hoctuan.codingforum.repository.chat.MessageRepository;
import com.hoctuan.codingforum.service.chat.MessageService;

import java.util.UUID;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, MessageResponseDTO, MessageRequestDTO, UUID>
        implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        super(messageRepository, messageMapper);
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }
}
