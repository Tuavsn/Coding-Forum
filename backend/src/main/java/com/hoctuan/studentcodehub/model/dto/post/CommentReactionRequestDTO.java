package com.hoctuan.studentcodehub.model.dto.post;

import com.hoctuan.studentcodehub.common.BaseRequestDTO;
import com.hoctuan.studentcodehub.constant.ReactionType;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.model.entity.post.PostComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CommentReactionRequestDTO extends BaseRequestDTO {
    private PostComment postComment;

    private User user;

    private ReactionType reactionType;
}
