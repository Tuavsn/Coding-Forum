package com.hoctuan.codingforum.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

import com.hoctuan.codingforum.common.BaseResponseDTO;
import com.hoctuan.codingforum.constant.PostStatus;
import com.hoctuan.codingforum.model.dto.user.UserResponseDTO;

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

    private List<TopicResponseDTO> topics;

    private List<PostImageDTO> postImage;

    private List<PostCommentResponseDTO> postComment;

    private List<PostReactionResponseDTO> postReactions;
}
