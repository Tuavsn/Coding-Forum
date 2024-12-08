package com.hoctuan.codingforum.service.post;

import java.util.UUID;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.model.dto.post.PostCommentRequestDTO;
import com.hoctuan.codingforum.model.dto.post.PostCommentResponseDTO;

public interface PostCommentService extends BaseService<PostCommentResponseDTO, PostCommentRequestDTO, UUID> {
}
