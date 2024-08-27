package com.hoctuan.studentcodehub.service.chat.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.chat.MessageRequestDTO;
import com.hoctuan.studentcodehub.model.dto.chat.MessageResponseDTO;
import com.hoctuan.studentcodehub.model.entity.chat.Message;
import com.hoctuan.studentcodehub.model.mapper.MessageMapper;
import com.hoctuan.studentcodehub.repository.chat.MessageRepository;
import com.hoctuan.studentcodehub.service.chat.MessageService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageServiceImpl extends BaseServiceImpl<
        Message,
        MessageResponseDTO,
        MessageRequestDTO,
        UUID> implements MessageService {
    private MessageRepository messageRepository;
    private MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        super(messageRepository, messageMapper);
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }
}
