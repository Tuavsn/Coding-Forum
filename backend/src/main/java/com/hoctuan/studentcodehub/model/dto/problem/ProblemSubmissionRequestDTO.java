package com.hoctuan.studentcodehub.model.dto.problem;

import com.hoctuan.studentcodehub.common.BaseRequestDTO;
import com.hoctuan.studentcodehub.constant.ProblemSubmissionLanguageType;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.model.entity.problem.Problem;
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
public class ProblemSubmissionRequestDTO extends BaseRequestDTO {
    private Problem problem;

    private User user;

    private String code;

    private ProblemSubmissionLanguageType languageType;
}
