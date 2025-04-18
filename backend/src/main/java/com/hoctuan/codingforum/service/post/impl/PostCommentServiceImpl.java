package com.hoctuan.codingforum.service.post.impl;

import lombok.extern.log4j.Log4j2;
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

/**
 * Implementation of the Post Comment Service
 * Handles operations related to post comments, including creation and deletion
 */
@Log4j2
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

    /**
     * Save a new post comment
     * 
     * @param dto Comment data transfer object
     * @return Created comment
     * @throws CustomException if user is not authenticated
     */
    @Override
    @Transactional
    public PostCommentResponseDTO save(PostCommentRequestDTO dto) {
        log.debug("Creating new comment for post ID: {}", dto.getPost() != null ? dto.getPost().getId() : null);
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> {
                    log.error("Unauthorized attempt to create comment");
                    return new CustomException(ErrorCode.UNAUTHORIZED);
                }));
        UserRequestDTO userDTO = UserRequestDTO.builder().id(userId).build();
        dto.setUser(userDTO);
        
        PostCommentResponseDTO result = super.save(dto);
        log.info("Comment created successfully by user: {} for post ID: {}", 
                userId, dto.getPost() != null ? dto.getPost().getId() : null);
        return result;
    }

    /**
     * Delete a post comment
     * 
     * @param id Comment ID to delete
     * @throws CustomException if user is not authenticated or not the comment author
     */
    @Override
    @Transactional
    public void delete(UUID id) {
        log.debug("Attempting to delete comment with ID: {}", id);
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> {
                    log.error("Unauthorized attempt to delete comment ID: {}", id);
                    return new CustomException(ErrorCode.UNAUTHORIZED);
                }));
                
        PostComment existedComment = postCommentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Comment not found with ID: {}", id);
                    return new CustomException(ErrorCode.COMMENT_NOT_FOUND);
                });
                
        // check if user is comment author
        validateTheAuthor(existedComment, userId);
        
        postCommentRepository.delete(existedComment);
        log.info("Comment with ID: {} successfully deleted by user: {}", id, userId);
    }

    /**
     * Validate that the user is the author of the comment
     * 
     * @param postComment The comment to check
     * @param userId User attempting to modify/delete the comment
     * @throws CustomException if user is not the comment author
     */
    private void validateTheAuthor(PostComment postComment, UUID userId) {
        if (!postComment.getUser().getId().equals(userId)) {
            log.warn("User {} attempted to delete comment {} created by another user", 
                    userId, postComment.getId());
            throw new CustomException(ErrorCode.WRONG_AUTHOR);
        }
        log.debug("User {} validated as author of comment {}", userId, postComment.getId());
    }
}