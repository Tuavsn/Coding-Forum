package com.hoctuan.codingforum.model.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

import com.hoctuan.codingforum.common.BaseRequestDTO;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class PostRequestDTO extends BaseRequestDTO {
    private UserRequestDTO user;
    
    private Set<TopicRequestDTO> topics;

    @NotBlank(message = "Bạn chưa nhập header")
    @Size(max = 100, message = "Header quá dài")
    private String header;

    private String content;

    private List<PostImageDTO> postImage;
}
