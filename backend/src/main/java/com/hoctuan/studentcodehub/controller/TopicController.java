package com.hoctuan.studentcodehub.controller;

import com.hoctuan.studentcodehub.common.BaseController;
import com.hoctuan.studentcodehub.model.dto.post.TopicRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.TopicResponseDTO;
import com.hoctuan.studentcodehub.model.entity.post.Topic;
import com.hoctuan.studentcodehub.service.post.TopicService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/topic")
public class TopicController extends BaseController<
        Topic,
        TopicResponseDTO,
        TopicRequestDTO,
        UUID> {
    private TopicService topicService;

    public TopicController(TopicService topicService) {
        super(topicService);
        this.topicService = topicService;
    }
}
