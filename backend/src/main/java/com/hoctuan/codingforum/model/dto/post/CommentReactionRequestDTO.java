package com.hoctuan.codingforum.model.dto.post;

import com.hoctuan.codingforum.common.BaseRequestDTO;
import com.hoctuan.codingforum.constant.ReactionType;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.post.PostComment;

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
