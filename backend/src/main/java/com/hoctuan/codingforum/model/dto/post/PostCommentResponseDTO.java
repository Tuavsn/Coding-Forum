package com.hoctuan.codingforum.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

import com.hoctuan.codingforum.common.BaseResponseDTO;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class PostCommentResponseDTO extends BaseResponseDTO {
    private PostRequestDTO post;

    private UserRequestDTO user;

    private String content;

    private List<CommentReactionResponseDTO> commentReactions;
}
