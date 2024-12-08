package com.hoctuan.codingforum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoctuan.codingforum.common.BaseController;
import com.hoctuan.codingforum.model.dto.post.TopicRequestDTO;
import com.hoctuan.codingforum.model.dto.post.TopicResponseDTO;
import com.hoctuan.codingforum.model.entity.post.Topic;
import com.hoctuan.codingforum.service.post.TopicService;

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
