package com.hoctuan.studentcodehub.model.dto.post;

import com.hoctuan.studentcodehub.common.BaseRequestDTO;
import com.hoctuan.studentcodehub.model.dto.user.UserRequestDTO;
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
public class PostCommentRequestDTO extends BaseRequestDTO {
    private PostRequestDTO post;

    private UserRequestDTO user;

    private String content;
}
