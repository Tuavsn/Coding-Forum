package com.hoctuan.studentcodehub.service.post.impl;

import com.hoctuan.studentcodehub.constant.ReactionType;
import com.hoctuan.studentcodehub.exception.CustomException;
import com.hoctuan.studentcodehub.exception.NotFoundException;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.model.entity.post.CommentReaction;
import com.hoctuan.studentcodehub.model.entity.post.PostComment;
import com.hoctuan.studentcodehub.model.mapper.CommentReactionMapper;
import com.hoctuan.studentcodehub.repository.post.CommentReactionRepository;
import com.hoctuan.studentcodehub.repository.post.PostCommentRepository;
import com.hoctuan.studentcodehub.service.common.AuthContext;
import com.hoctuan.studentcodehub.service.post.CommentReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentReactionServiceImpl implements CommentReactionService {
    @Autowired
    private CommentReactionRepository commentReactionRepository;
    @Autowired
    private PostCommentRepository postCommentRepository;
    @Autowired
    private CommentReactionMapper commentReactionMapper;
    @Autowired
    private AuthContext authContext;

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
