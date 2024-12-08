package com.hoctuan.codingforum.model.dto.post;

import com.hoctuan.codingforum.common.BaseRequestDTO;
import com.hoctuan.codingforum.constant.ReactionType;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;

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
public class PostReactionRequestDTO extends BaseRequestDTO {
    private PostRequestDTO post;

    private UserRequestDTO user;

    private ReactionType reactionType;
}
