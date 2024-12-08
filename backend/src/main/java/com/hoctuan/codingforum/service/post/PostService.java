package com.hoctuan.codingforum.service.post;

import java.util.List;
import java.util.UUID;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.model.dto.post.PostRequestDTO;
import com.hoctuan.codingforum.model.dto.post.PostResponseDTO;

public interface PostService extends BaseService<PostResponseDTO, PostRequestDTO, UUID> {
    public List<PostResponseDTO> findPostByUser(UUID userId);
}
