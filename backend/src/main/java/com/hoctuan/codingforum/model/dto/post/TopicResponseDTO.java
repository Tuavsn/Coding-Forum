package com.hoctuan.codingforum.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import com.hoctuan.codingforum.common.BaseResponseDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TopicResponseDTO extends BaseResponseDTO {
    private String name;
}
