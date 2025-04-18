package com.hoctuan.codingforum.service.post.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoctuan.codingforum.constant.ErrorCode;
import com.hoctuan.codingforum.constant.ReactionType;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.post.CommentReaction;
import com.hoctuan.codingforum.model.entity.post.PostComment;
import com.hoctuan.codingforum.repository.post.CommentReactionRepository;
import com.hoctuan.codingforum.repository.post.PostCommentRepository;
import com.hoctuan.codingforum.service.common.AuthContext;
import com.hoctuan.codingforum.service.post.CommentReactionService;

import java.util.UUID;

/**
 * Implementation of the Comment Reaction Service
 * Handles operations related to reactions (like, dislike) on comments
 */
@Log4j2
@Service
public class CommentReactionServiceImpl implements CommentReactionService {
    private final CommentReactionRepository commentReactionRepository;
    private final PostCommentRepository postCommentRepository;
    private final AuthContext authContext;

    public CommentReactionServiceImpl(CommentReactionRepository commentReactionRepository,
            PostCommentRepository postCommentRepository, AuthContext authContext) {
        this.commentReactionRepository = commentReactionRepository;
        this.postCommentRepository = postCommentRepository;
        this.authContext = authContext;
    }

    /**
     * Add or toggle a reaction on a comment
     * If the reaction already exists with the same type, it will be removed
     * If the reaction exists with a different type, it will be updated
     * If no reaction exists, a new one will be created
     * 
     * @param commentId The ID of the comment to react to
     * @param type      The type of reaction (LIKE, DISLIKE)
     * @throws CustomException if user is not authenticated
     */
    @Override
    @Transactional
    public void reactionComment(UUID commentId, ReactionType type) {
        log.debug("Processing reaction of type {} for comment ID: {}", type, commentId);

        UUID userId = UUID.fromString(
                authContext.getCurrentUserLogin().orElseThrow(() -> {
                    log.error("Unauthorized attempt to react to comment ID: {}", commentId);
                    return new CustomException(ErrorCode.UNAUTHORIZED);
                }));

        log.debug("User {} attempting to react to comment {}", userId, commentId);

        commentReactionRepository.findByUserIdAndCommentId(userId, commentId).ifPresentOrElse(existedReaction -> {
            if (existedReaction.getReactionType().equals(type)) {
                // Remove reaction if same type (toggle off)
                log.debug("Removing existing {} reaction from user {} on comment {}",
                        type, userId, commentId);
                commentReactionRepository.delete(existedReaction);
                log.info("Reaction removed successfully for comment ID: {} by user ID: {}",
                        commentId, userId);
            } else {
                // Update reaction type
                log.debug("Changing reaction from {} to {} for user {} on comment {}",
                        existedReaction.getReactionType(), type, userId, commentId);
                existedReaction.setReactionType(type);
                commentReactionRepository.save(existedReaction);
                log.info("Reaction updated to {} for comment ID: {} by user ID: {}",
                        type, commentId, userId);
            }
        }, () -> {
            // Create new reaction
            log.debug("Creating new {} reaction for user {} on comment {}", type, userId, commentId);
            User user = User.builder().id(userId).build();
            PostComment comment = PostComment.builder().id(commentId).build();
            CommentReaction reaction = CommentReaction.builder()
                    .user(user)
                    .postComment(comment)
                    .reactionType(type)
                    .build();
            commentReactionRepository.save(reaction);
            log.info("New {} reaction created for comment ID: {} by user ID: {}",
                    type, commentId, userId);
        });
    }
}