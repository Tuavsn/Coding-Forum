package com.hoctuan.studentcodehub.model.dto.problem;

import com.hoctuan.studentcodehub.common.BaseResponseDTO;
import com.hoctuan.studentcodehub.constant.ProblemType;
import com.hoctuan.studentcodehub.model.dto.user.UserResponseDTO;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.model.entity.problem.ProblemComment;
import com.hoctuan.studentcodehub.model.entity.problem.ProblemSubmission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

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

    private String otherProps;

    private String testCases;

    private UserResponseDTO author;

    private double totalScore;

    private Set<ProblemSubmission> problemSubmissions;

    private Set<ProblemComment> comments;
}
