package com.hoctuan.studentcodehub.model.dto.post;

import com.hoctuan.studentcodehub.common.BaseResponseDTO;
import com.hoctuan.studentcodehub.constant.PostStatus;
import com.hoctuan.studentcodehub.model.dto.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class PostResponseDTO extends BaseResponseDTO {
    private UserResponseDTO user;

    private String header;

    private String content;

    private PostStatus status;

    private List<PostImageDTO> postImage;

    private List<PostCommentResponseDTO> postComment;

    private List<PostReactionResponseDTO> postReactions;
}
