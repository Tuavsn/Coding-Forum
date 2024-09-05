package com.hoctuan.studentcodehub.model.dto.post;

import com.hoctuan.studentcodehub.common.BaseResponseDTO;
import com.hoctuan.studentcodehub.constant.ReactionType;
import com.hoctuan.studentcodehub.model.dto.user.UserResponseDTO;
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
public class CommentReactionResponseDTO extends BaseResponseDTO {
    private UserResponseDTO user;

    private ReactionType reactionType;
}
