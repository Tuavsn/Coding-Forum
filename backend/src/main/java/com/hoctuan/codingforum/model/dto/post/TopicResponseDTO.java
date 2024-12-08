package com.hoctuan.codingforum.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

import com.hoctuan.codingforum.common.BaseResponseDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TopicResponseDTO extends BaseResponseDTO {
    private String name;

    private Set<PostResponseDTO> posts = new HashSet<>();
}
