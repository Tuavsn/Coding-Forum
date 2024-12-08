package com.hoctuan.codingforum.model.dto.post;

import com.hoctuan.codingforum.common.BaseResponseDTO;

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
public class PostImageDTO extends BaseResponseDTO {
    private String image;
}
