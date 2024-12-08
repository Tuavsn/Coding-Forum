package com.hoctuan.codingforum.model.dto.post;

import com.hoctuan.codingforum.common.BaseRequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class TopicRequestDTO extends BaseRequestDTO {
    @NotBlank(message = "Bạn chưa nhập tên Topic")
    @Size(max = 50, message = "Tên toipc quá dài")
    private String name;
}
