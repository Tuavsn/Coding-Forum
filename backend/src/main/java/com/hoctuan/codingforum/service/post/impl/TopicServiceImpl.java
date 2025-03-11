package com.hoctuan.codingforum.service.post.impl;

import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.model.dto.post.TopicRequestDTO;
import com.hoctuan.codingforum.model.dto.post.TopicResponseDTO;
import com.hoctuan.codingforum.model.entity.post.Topic;
import com.hoctuan.codingforum.model.mapper.TopicMapper;
import com.hoctuan.codingforum.repository.post.TopicRepository;
import com.hoctuan.codingforum.service.post.TopicService;

import java.util.UUID;

@Service
public class TopicServiceImpl extends BaseServiceImpl<Topic, TopicResponseDTO, TopicRequestDTO, UUID>
        implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
        super(topicRepository, topicMapper);
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }
}
