package com.hoctuan.studentcodehub.service.post;

import com.hoctuan.studentcodehub.common.BaseService;
import com.hoctuan.studentcodehub.model.dto.post.PostCommentRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostCommentResponseDTO;

import java.util.UUID;

public interface PostCommentService extends BaseService<PostCommentResponseDTO, PostCommentRequestDTO, UUID> {
}
