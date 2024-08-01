package com.hoctuan.studentcodehub.service.chat.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.chat.MessageResponseDTO;
import com.hoctuan.studentcodehub.model.dto.chat.MessgeRequestDTO;
import com.hoctuan.studentcodehub.model.entity.chat.Message;
import com.hoctuan.studentcodehub.service.chat.MessageService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageServiceImpl extends BaseServiceImpl<
        Message,
        MessageResponseDTO,
        MessgeRequestDTO,
        UUID> implements MessageService {
}
