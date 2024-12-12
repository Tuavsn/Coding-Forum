package com.hoctuan.codingforum.service.post.impl;

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

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentReactionServiceImpl implements CommentReactionService {
    private final CommentReactionRepository commentReactionRepository;
    private final PostCommentRepository postCommentRepository;
    private final AuthContext authContext;

    public void likeComment(UUID commentId) {
        User user = authContext.getUserAuthenticated();

        PostComment existedPostComment = postCommentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Không tìm thấy comment"));

        if(!existedPostComment.getCommentReactions().isEmpty()) {
            boolean isLiked = existedPostComment.getCommentReactions().stream().anyMatch(
                    (reaction) -> reaction.getUser().equals(user) && reaction.getReactionType().equals(ReactionType.LIKE)
            );

            if (isLiked) {
                throw new CustomException("Bạn đã like comment", HttpStatus.BAD_REQUEST.value());
            }

            existedPostComment.getCommentReactions().forEach(
                    (reaction) -> {
                        if(reaction.getUser().equals(user) && reaction.getReactionType().equals(ReactionType.DISLIKE)) {
                            commentReactionRepository.delete(reaction);
                        }
                    }
            );
        }

        CommentReaction newCommentReaction = new CommentReaction();

        newCommentReaction.setPostComment(existedPostComment);
        newCommentReaction.setUser(user);
        newCommentReaction.setReactionType(ReactionType.LIKE);

        commentReactionRepository.save(newCommentReaction);
    }

    public void dislikeComment(UUID commentId) {
        User user = authContext.getUserAuthenticated();

        PostComment existedPostComment = postCommentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Không tìm thấy comment"));

        if(!existedPostComment.getCommentReactions().isEmpty()) {
            boolean isDisliked = existedPostComment.getCommentReactions().stream().anyMatch(
                    (reaction) -> reaction.getUser().equals(user) && reaction.getReactionType().equals(ReactionType.DISLIKE)
            );

            if (isDisliked) {
                throw new CustomException("Bạn đã dislike comment", HttpStatus.BAD_REQUEST.value());
            }

            existedPostComment.getCommentReactions().forEach(
                    (reaction) -> {
                        if(reaction.getUser().equals(user) && reaction.getReactionType().equals(ReactionType.LIKE)) {
                            commentReactionRepository.delete(reaction);
                        }
                    }
            );
        }

        CommentReaction newCommentReaction = new CommentReaction();

        newCommentReaction.setPostComment(existedPostComment);
        newCommentReaction.setUser(user);
        newCommentReaction.setReactionType(ReactionType.DISLIKE);

        commentReactionRepository.save(newCommentReaction);
    }
}
