package com.hoctuan.codingforum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoctuan.codingforum.common.BaseController;
import com.hoctuan.codingforum.model.dto.chat.MessageRequestDTO;
import com.hoctuan.codingforum.model.dto.chat.MessageResponseDTO;
import com.hoctuan.codingforum.model.entity.chat.Message;

import java.util.UUID;

@RestController
@RequestMapping("${spring.api.prefix}/chat")
public class ChatController {
}

//public class ChatController extends BaseController<Message,
//        MessageResponseDTO,
//        MessageRequestDTO,
//        UUID> {
//}
//
