package com.hoctuan.codingforum.model.dto.problem;

import com.hoctuan.codingforum.common.BaseResponseDTO;
import com.hoctuan.codingforum.model.dto.auth.AuthResponseDTO;

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
public class ProblemCommentResponseDTO extends BaseResponseDTO {
    private AuthResponseDTO user;

    private String content;
}
