package com.hoctuan.studentcodehub.controller;

import com.hoctuan.studentcodehub.model.entity.chat.Message;
import com.hoctuan.studentcodehub.model.entity.notify.Notify;
import com.hoctuan.studentcodehub.model.entity.post.PostComment;
import com.hoctuan.studentcodehub.model.entity.post.PostReaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocketController {
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/comment")
    public void getComment(@Payload PostComment comment) {
        template.convertAndSend("/comment", comment);
    }

    @MessageMapping("/reaction")
    public void getComment(@Payload PostReaction reaction) {
        template.convertAndSend("/reaction", reaction);
    }

    @MessageMapping("/message")
    public void getComment(@Payload Message message) {
        template.convertAndSendToUser(String.valueOf(message.getTarget()), "/message", message);
    }

    @MessageMapping("/notify")
    public void getComment(@Payload Notify notify) {
        template.convertAndSendToUser(String.valueOf(notify.getTarget()), "/notify", notify);
    }
}
