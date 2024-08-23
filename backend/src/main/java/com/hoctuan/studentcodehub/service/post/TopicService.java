package com.hoctuan.studentcodehub.service.post;

import com.hoctuan.studentcodehub.common.BaseService;
import com.hoctuan.studentcodehub.model.dto.post.TopicRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.TopicResponseDTO;

import java.util.UUID;

public interface TopicService extends BaseService<TopicResponseDTO, TopicRequestDTO, UUID> {
}
