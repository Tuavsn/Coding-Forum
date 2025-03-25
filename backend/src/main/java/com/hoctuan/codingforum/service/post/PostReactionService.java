package com.hoctuan.codingforum.service.post;

import java.util.UUID;

import com.hoctuan.codingforum.constant.ReactionType;

public interface PostReactionService {
    public void reactionPost(UUID postId, ReactionType type);
}
