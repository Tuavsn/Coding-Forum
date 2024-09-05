package com.hoctuan.studentcodehub.service.post;

import java.util.UUID;

public interface CommentReactionService {
    public void likeComment(UUID commentId);

    public void dislikeComment(UUID commentId);
}
