package com.hoctuan.codingforum.service.post.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.exception.NotFoundException;
import com.hoctuan.codingforum.model.dto.post.PostCommentRequestDTO;
import com.hoctuan.codingforum.model.dto.post.PostCommentResponseDTO;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.post.PostComment;
import com.hoctuan.codingforum.model.mapper.PostCommentMapper;
import com.hoctuan.codingforum.repository.account.UserRepository;
import com.hoctuan.codingforum.repository.post.PostCommentRepository;
import com.hoctuan.codingforum.service.common.AuthContext;
import com.hoctuan.codingforum.service.post.PostCommentService;

import java.util.UUID;

@Service
public class PostCommentServiceImpl
        extends BaseServiceImpl<PostComment, PostCommentResponseDTO, PostCommentRequestDTO, UUID>
        implements PostCommentService {
    private final PostCommentRepository postCommentRepository;
    private final PostCommentMapper postCommentMapper;
    private final UserRepository userRepository;
    private final AuthContext authContext;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository, PostCommentMapper postCommentMapper,
            UserRepository userRepository, AuthContext authContext) {
        super(postCommentRepository, postCommentMapper);
        this.postCommentRepository = postCommentRepository;
        this.postCommentMapper = postCommentMapper;
        this.userRepository = userRepository;
        this.authContext = authContext;
    }

    @Override
    @Transactional
    public PostCommentResponseDTO save(PostCommentRequestDTO dto) {
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value())));
        User existedUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Tài khoản không tồn tại", HttpStatus.BAD_REQUEST.value()));
        // check if user is comment author
        if (dto.getId() != null) {
            PostComment existedComment = postCommentRepository.findById(dto.getId())
                    .orElseThrow(() -> new NotFoundException("Id không tìm thấy"));
            if (existedComment.getUser().getId() != user.getId()) {
                throw new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value());
            }
        }
        UserRequestDTO userDTO = UserRequestDTO.builder().id(user.getId()).build();
        dto.setUser(userDTO);
        return super.forceSave(dto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value())));
        PostComment existedComment = postCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id không tìm thấy"));
        if (existedComment.getUser().getId() != user.getId()) {
            throw new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value());
        }
        postCommentRepository.delete(existedComment);
    }
}
