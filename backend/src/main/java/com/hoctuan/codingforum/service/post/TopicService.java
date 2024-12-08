package com.hoctuan.codingforum.service.post;

import java.util.UUID;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.model.dto.post.TopicRequestDTO;
import com.hoctuan.codingforum.model.dto.post.TopicResponseDTO;

public interface TopicService extends BaseService<TopicResponseDTO, TopicRequestDTO, UUID> {
}
