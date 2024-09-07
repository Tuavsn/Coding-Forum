package com.hoctuan.studentcodehub.model.dto.post;

import com.hoctuan.studentcodehub.common.BaseRequestDTO;
import com.hoctuan.studentcodehub.model.dto.user.UserRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class PostRequestDTO extends BaseRequestDTO {
    private TopicRequestDTO topic;

    private UserRequestDTO user;

    @NotBlank(message = "Bạn chưa nhập header")
    @Size(max = 100, message = "Header quá dài")
    private String header;

    private String content;

    private List<PostImageDTO> postImage;
}
