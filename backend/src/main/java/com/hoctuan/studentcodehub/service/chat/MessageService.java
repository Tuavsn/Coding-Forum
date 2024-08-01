package com.hoctuan.studentcodehub.service.chat;

import com.hoctuan.studentcodehub.common.BaseService;
import com.hoctuan.studentcodehub.model.dto.chat.MessageResponseDTO;
import com.hoctuan.studentcodehub.model.dto.chat.MessgeRequestDTO;

import java.util.UUID;

public interface MessageService extends BaseService<MessageResponseDTO, MessgeRequestDTO, UUID> {
}
