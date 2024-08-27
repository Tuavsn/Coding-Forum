package com.hoctuan.studentcodehub.service.post.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.post.PostCommentRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostCommentResponseDTO;
import com.hoctuan.studentcodehub.model.dto.user.UserRequestDTO;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.model.entity.post.PostComment;
import com.hoctuan.studentcodehub.model.mapper.PostCommentMapper;
import com.hoctuan.studentcodehub.repository.post.PostCommentRepository;
import com.hoctuan.studentcodehub.service.common.AuthContext;
import com.hoctuan.studentcodehub.service.post.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostCommentServiceImpl extends BaseServiceImpl<
        PostComment,
        PostCommentResponseDTO,
        PostCommentRequestDTO,
        UUID> implements PostCommentService {
    private PostCommentRepository postCommentRepository;
    private PostCommentMapper postCommentMapper;
    @Autowired
    private AuthContext authContext;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository, PostCommentMapper postCommentMapper) {
        super(postCommentRepository, postCommentMapper);
        this.postCommentRepository = postCommentRepository;
        this.postCommentMapper = postCommentMapper;
    }

    @Override
    public PostCommentResponseDTO save(PostCommentRequestDTO dto) {
        User user = authContext.getUserAuthenticated();
        UserRequestDTO userDTO = UserRequestDTO.builder().id(user.getId()).build();
        dto.setUser(userDTO);
        return super.save(dto);
    }
}
