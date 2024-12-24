package com.hoctuan.codingforum.model.dto.problem;

import com.hoctuan.codingforum.common.BaseRequestDTO;
import com.hoctuan.codingforum.constant.ProblemType;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;

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
public class ProblemRequestDTO extends BaseRequestDTO {
    private String title;

    private String description;

    private String example;

    private String tags;

    private String thumbnail;

    private ProblemType difficulty;

    private String testCases;

    private UserRequestDTO author;

    private double totalScore;
}
