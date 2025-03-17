package com.hoctuan.codingforum.service.post.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.constant.ErrorCode;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.model.dto.post.PostCommentRequestDTO;
import com.hoctuan.codingforum.model.dto.post.PostCommentResponseDTO;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.entity.post.PostComment;
import com.hoctuan.codingforum.model.mapper.PostCommentMapper;
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
    private final AuthContext authContext;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository, PostCommentMapper postCommentMapper,
            AuthContext authContext) {
        super(postCommentRepository, postCommentMapper, PostComment.class);
        this.postCommentRepository = postCommentRepository;
        this.postCommentMapper = postCommentMapper;
        this.authContext = authContext;
    }

    @Override
    @Transactional
    public PostCommentResponseDTO save(PostCommentRequestDTO dto) {
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED)));
        UserRequestDTO userDTO = UserRequestDTO.builder().id(userId).build();
        dto.setUser(userDTO);
        return super.save(dto);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED)));
        PostComment existedComment = postCommentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        // check if user is comment author
        validateTheAuthor(existedComment, userId);
        postCommentRepository.delete(existedComment);
    }

    /**
     * Check if client's request is post author
     * 
     * @param postComment
     * @param userId
     * @return
     */
    private void validateTheAuthor(PostComment postComment, UUID userId) {
        if (postComment.getUser().getId() != userId) {
            throw new CustomException(ErrorCode.WRONG_AUTHOR);
        }
    }
}
