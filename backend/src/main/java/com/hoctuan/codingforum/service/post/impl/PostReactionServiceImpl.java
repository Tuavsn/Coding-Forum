package com.hoctuan.codingforum.service.post.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoctuan.codingforum.constant.ErrorCode;
import com.hoctuan.codingforum.constant.ReactionType;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.post.Post;
import com.hoctuan.codingforum.model.entity.post.PostReaction;
import com.hoctuan.codingforum.repository.post.PostReactionRepository;
import com.hoctuan.codingforum.repository.post.PostRepository;
import com.hoctuan.codingforum.service.common.AuthContext;
import com.hoctuan.codingforum.service.post.PostReactionService;

import java.util.UUID;

/**
 * Implementation of the Post Reaction Service
 * Handles operations related to reactions (like, dislike) on posts
 */
@Log4j2
@Service
public class PostReactionServiceImpl implements PostReactionService {
    private final PostReactionRepository postReactionRepository;
    private final PostRepository postRepository;
    private final AuthContext authContext;

    public PostReactionServiceImpl(PostReactionRepository postReactionRepository, PostRepository postRepository,
            AuthContext authContext) {
        this.postReactionRepository = postReactionRepository;
        this.postRepository = postRepository;
        this.authContext = authContext;
    }

    /**
     * Add or toggle a reaction on a post
     * If the reaction already exists with the same type, it will be removed
     * If the reaction exists with a different type, it will be updated
     * If no reaction exists, a new one will be created
     * 
     * @param postId The ID of the post to react to
     * @param type   The type of reaction (LIKE, DISLIKE)
     * @throws CustomException if user is not authenticated
     */
    @Override
    @Transactional
    public void reactionPost(UUID postId, ReactionType type) {
        log.debug("Processing reaction of type {} for post ID: {}", type, postId);
        
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> {
                    log.error("Unauthorized attempt to react to post ID: {}", postId);
                    return new CustomException(ErrorCode.UNAUTHORIZED);
                }));
                
        log.debug("User {} attempting to react to post {}", userId, postId);
        
        postReactionRepository.findByUserIdAndPostId(userId, postId).ifPresentOrElse(existedReaction -> {
            if (existedReaction.getReactionType().equals(type)) {
                // Remove reaction if same type (toggle off)
                log.debug("Removing existing {} reaction from user {} on post {}", 
                        type, userId, postId);
                postReactionRepository.delete(existedReaction);
                log.info("Reaction removed successfully for post ID: {} by user ID: {}", 
                        postId, userId);
            } else {
                // Update reaction type
                log.debug("Changing reaction from {} to {} for user {} on post {}", 
                        existedReaction.getReactionType(), type, userId, postId);
                existedReaction.setReactionType(type);
                postReactionRepository.save(existedReaction);
                log.info("Reaction updated to {} for post ID: {} by user ID: {}", 
                        type, postId, userId);
            }
        }, () -> {
            // Create new reaction
            log.debug("Creating new {} reaction for user {} on post {}", type, userId, postId);
            User user = User.builder().id(userId).build();
            Post post = Post.builder().id(postId).build();
            postReactionRepository.save(PostReaction.builder()
                    .user(user)
                    .post(post)
                    .reactionType(type)
                    .build());
            log.info("New {} reaction created for post ID: {} by user ID: {}", 
                    type, postId, userId);
        });
    }
}