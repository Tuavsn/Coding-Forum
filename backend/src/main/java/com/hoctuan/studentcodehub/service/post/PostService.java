package com.hoctuan.studentcodehub.service.post;

import com.hoctuan.studentcodehub.common.BaseService;
import com.hoctuan.studentcodehub.model.dto.post.PostRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostResponseDTO;

import java.util.UUID;

public interface PostService extends BaseService<PostResponseDTO, PostRequestDTO, UUID> {
}
