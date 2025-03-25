package com.hoctuan.codingforum.service.post;

import java.util.UUID;

import com.hoctuan.codingforum.constant.ReactionType;

public interface CommentReactionService {
    public void reactionComment(UUID commentId, ReactionType type);
}
