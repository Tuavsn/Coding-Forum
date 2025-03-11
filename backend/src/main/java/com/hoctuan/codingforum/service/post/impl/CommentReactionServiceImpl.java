package com.hoctuan.codingforum.service.post.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.constant.ReactionType;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.exception.NotFoundException;
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

    public void likeComment(UUID commentId) {
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin().orElseThrow(
            () -> new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value()))); 
        PostComment existedPostComment = postCommentRepository.findById(commentId).orElseThrow(
            () -> new NotFoundException("Không tìm thấy comment hợp lệ với ID: " + commentId));
        if (!existedPostComment.getCommentReactions().isEmpty()) {
            existedPostComment.getCommentReactions().forEach(
            (reaction) -> {
                if (reaction.getUser().getId().equals(userId)
                    && reaction.getReactionType().equals(ReactionType.DISLIKE)
                    || reaction.getReactionType().equals(ReactionType.LIKE)) {
                    commentReactionRepository.delete(reaction);
                }
            });
        }
        CommentReaction newCommentReaction = new CommentReaction();
        newCommentReaction.setPostComment(existedPostComment);
        newCommentReaction.setUser(user);
        newCommentReaction.setReactionType(ReactionType.LIKE);
        commentReactionRepository.save(newCommentReaction);
    }

    public void dislikeComment(UUID commentId) {
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin().orElseThrow(
            () -> new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value()))); 
        PostComment existedPostComment = postCommentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundException("Không tìm thấy comment"));
        if (!existedPostComment.getCommentReactions().isEmpty()) {
            boolean isDisliked = existedPostComment.getCommentReactions().stream().anyMatch(
                (reaction) -> reaction.getUser().getId().equals(userId)
                && reaction.getReactionType().equals(ReactionType.DISLIKE));
            if (isDisliked) {
                throw new CustomException("Bạn đã dislike comment", HttpStatus.BAD_REQUEST.value());
            }
            existedPostComment.getCommentReactions().forEach(
            (reaction) -> {
                if (reaction.getUser().getId().equals(userId) && reaction.getReactionType().equals(ReactionType.LIKE)) {
                    commentReactionRepository.delete(reaction);
                }
            });
        }
        CommentReaction newCommentReaction = new CommentReaction();
        newCommentReaction.setPostComment(existedPostComment);
        newCommentReaction.setUser(user);
        newCommentReaction.setReactionType(ReactionType.DISLIKE);
        commentReactionRepository.save(newCommentReaction);
    }

    public void removeReaction(PostComment existedPostComment, UUID userId) {
        existedPostComment.getCommentReactions().stream()
                .filter((reaction) -> reaction.getUser().getId().equals(userId))
                .forEach(commentReactionRepository::delete);
    }
}
