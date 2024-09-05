package com.hoctuan.studentcodehub.service.post.impl;

import com.hoctuan.studentcodehub.constant.ReactionType;
import com.hoctuan.studentcodehub.exception.CustomException;
import com.hoctuan.studentcodehub.exception.NotFoundException;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.model.entity.post.Post;
import com.hoctuan.studentcodehub.model.entity.post.PostReaction;
import com.hoctuan.studentcodehub.model.mapper.PostReactionMapper;
import com.hoctuan.studentcodehub.repository.post.PostReactionRepository;
import com.hoctuan.studentcodehub.repository.post.PostRepository;
import com.hoctuan.studentcodehub.service.common.AuthContext;
import com.hoctuan.studentcodehub.service.post.PostReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostReactionServiceImpl implements PostReactionService {
    @Autowired
    private PostReactionRepository postReactionRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostReactionMapper postReactionMapper;
    @Autowired
    private AuthContext authContext;

    public void likePost(UUID postId) {
        User user = authContext.getUserAuthenticated();

        Post existedPost = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Không tìm thấy bài Post"));

        if(!existedPost.getPostReactions().isEmpty()) {
            boolean isLiked = existedPost.getPostReactions().stream().anyMatch(
                    (reaction) -> reaction.getUser().equals(user) && reaction.getReactionType().equals(ReactionType.LIKE)
            );

            if (isLiked) {
                throw new CustomException("Bạn đã like bài Post", HttpStatus.BAD_REQUEST.value());
            }

            existedPost.getPostReactions().forEach(
                    (reaction) -> {
                        if(reaction.getUser().equals(user) && reaction.getReactionType().equals(ReactionType.DISLIKE)) {
                            postReactionRepository.delete(reaction);
                        }
                    }
            );
        }

        PostReaction newPostReaction = new PostReaction();

        newPostReaction.setPost(existedPost);
        newPostReaction.setUser(user);
        newPostReaction.setReactionType(ReactionType.LIKE);

        postReactionRepository.save(newPostReaction);
    }

    public void dislikePost(UUID postId) {
        User user = authContext.getUserAuthenticated();

        Post existedPost = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Không tìm thấy bài Post"));

        if(!existedPost.getPostReactions().isEmpty()) {
            boolean isDisliked = existedPost.getPostReactions().stream().anyMatch(
                    (reaction) -> reaction.getUser().equals(user) && reaction.getReactionType().equals(ReactionType.DISLIKE)
            );

            if (isDisliked) {
                throw new CustomException("Bạn đã dislike bài Post", HttpStatus.BAD_REQUEST.value());
            }

            existedPost.getPostReactions().forEach(
                    (reaction) -> {
                        if(reaction.getUser().equals(user) && reaction.getReactionType().equals(ReactionType.LIKE)) {
                            postReactionRepository.delete(reaction);
                        }
                    }
            );
        }

        PostReaction newPostReaction = new PostReaction();

        newPostReaction.setPost(existedPost);
        newPostReaction.setUser(user);
        newPostReaction.setReactionType(ReactionType.DISLIKE);

        postReactionRepository.save(newPostReaction);
    }
}
