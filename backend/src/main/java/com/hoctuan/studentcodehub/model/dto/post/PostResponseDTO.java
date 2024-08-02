package com.hoctuan.studentcodehub.model.dto.post;

import com.hoctuan.studentcodehub.common.BaseResponseDTO;
import com.hoctuan.studentcodehub.constant.PostStatus;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.model.entity.post.PostComment;
import com.hoctuan.studentcodehub.model.entity.post.PostImage;
import com.hoctuan.studentcodehub.model.entity.post.PostReaction;
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
    private User user;

    private String header;

    private String content;

    private PostStatus status;

    private List<PostImage> postImage;

    private List<PostComment> postComment;

    private List<PostReaction> postReactions;
}
