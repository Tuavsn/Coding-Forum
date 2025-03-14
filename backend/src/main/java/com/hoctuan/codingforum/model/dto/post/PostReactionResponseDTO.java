package com.hoctuan.codingforum.model.dto.post;

import com.hoctuan.codingforum.common.BaseResponseDTO;
import com.hoctuan.codingforum.constant.ReactionType;
import com.hoctuan.codingforum.model.dto.user.UserResponseDTO;

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
public class PostReactionResponseDTO extends BaseResponseDTO {
    private UserResponseDTO user;

    private ReactionType reactionType;
}
