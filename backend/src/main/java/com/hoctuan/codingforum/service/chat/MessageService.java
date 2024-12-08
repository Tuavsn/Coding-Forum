package com.hoctuan.codingforum.service.chat;

import java.util.UUID;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.model.dto.chat.MessageRequestDTO;
import com.hoctuan.codingforum.model.dto.chat.MessageResponseDTO;

public interface MessageService extends BaseService<MessageResponseDTO, MessageRequestDTO, UUID> {
}
