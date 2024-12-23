package com.hoctuan.codingforum.model.dto.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import com.hoctuan.codingforum.common.BaseResponseDTO;
import com.hoctuan.codingforum.constant.ProblemType;
import com.hoctuan.codingforum.model.dto.user.UserResponseDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProblemResponseDTO extends BaseResponseDTO {
    private String title;

    private String description;

    private String example;

    private String tags;

    private ProblemType difficulty;

    private String testCases;

    private UserResponseDTO author;

    private double totalScore;

    private Set<ProblemCommentResponseDTO> comments;
}
