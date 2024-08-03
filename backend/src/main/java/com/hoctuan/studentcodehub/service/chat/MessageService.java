package com.hoctuan.studentcodehub.service.chat;

import com.hoctuan.studentcodehub.common.BaseService;
import com.hoctuan.studentcodehub.model.dto.chat.MessageRequestDTO;
import com.hoctuan.studentcodehub.model.dto.chat.MessageResponseDTO;

import java.util.UUID;

public interface MessageService extends BaseService<MessageResponseDTO, MessageRequestDTO, UUID> {
}
