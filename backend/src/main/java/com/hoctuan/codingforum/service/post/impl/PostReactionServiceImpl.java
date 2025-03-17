package com.hoctuan.codingforum.service.post.impl;

import org.springframework.stereotype.Service;
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

    public void reactionPost(UUID postId, ReactionType type) {
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED)));
        postReactionRepository.findByUserIdAndPostId(userId, postId).ifPresentOrElse(existedReaction -> {
            if (existedReaction.getReactionType().equals(type)) {
                postReactionRepository.delete(existedReaction);
            } else {
                existedReaction.setReactionType(type);
                postReactionRepository.save(existedReaction);
            }
        }, () -> {
            User user = User.builder().id(userId).build();
            Post post = Post.builder().id(postId).build();
            postReactionRepository.save(PostReaction.builder().user(user).post(post).build());
        });
    }
}
