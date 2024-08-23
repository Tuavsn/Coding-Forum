package com.hoctuan.studentcodehub.controller;

import com.hoctuan.studentcodehub.common.BaseController;
import com.hoctuan.studentcodehub.model.dto.chat.MessageRequestDTO;
import com.hoctuan.studentcodehub.model.dto.chat.MessageResponseDTO;
import com.hoctuan.studentcodehub.model.entity.chat.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/chat")
public class ChatController {
}

//public class ChatController extends BaseController<Message,
//        MessageResponseDTO,
//        MessageRequestDTO,
//        UUID> {
//}
//
