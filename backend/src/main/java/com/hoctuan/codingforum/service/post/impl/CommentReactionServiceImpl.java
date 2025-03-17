package com.hoctuan.codingforum.service.post.impl;

import org.springframework.stereotype.Service;
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

    public void reactionComment(UUID commentId, ReactionType type) {
        UUID userId = UUID.fromString(
                authContext.getCurrentUserLogin().orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED)));
        commentReactionRepository.findByUserIdAndCommentId(userId, commentId).ifPresentOrElse(existedReaction -> {
            if (existedReaction.getReactionType().equals(type)) {
                commentReactionRepository.delete(existedReaction);
            } else {
                existedReaction.setReactionType(type);
                commentReactionRepository.save(existedReaction);
            }
        }, () -> {
            User user = User.builder().id(userId).build();
            PostComment comment = PostComment.builder().id(commentId).build();
            commentReactionRepository.save(CommentReaction.builder().user(user).postComment(comment).build());
        });
    }
}
