package com.hoctuan.studentcodehub.service.post.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.post.TopicRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.TopicResponseDTO;
import com.hoctuan.studentcodehub.model.entity.post.Topic;
import com.hoctuan.studentcodehub.model.mapper.TopicMapper;
import com.hoctuan.studentcodehub.repository.post.TopicRepository;
import com.hoctuan.studentcodehub.service.post.TopicService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TopicServiceImpl extends BaseServiceImpl<
        Topic,
        TopicResponseDTO,
        TopicRequestDTO,
        UUID
        > implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
        super(topicRepository, topicMapper);
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }
}
