package com.hoctuan.studentcodehub.model.dto.problem;

import com.hoctuan.studentcodehub.common.BaseRequestDTO;
import com.hoctuan.studentcodehub.constant.ProblemType;
import com.hoctuan.studentcodehub.model.entity.account.User;
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

    private ProblemType difficulty;

    private String otherProps;

    private String testCases;

    private User author;

    private double totalScore;
}
