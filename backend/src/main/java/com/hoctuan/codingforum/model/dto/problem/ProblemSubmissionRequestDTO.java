package com.hoctuan.codingforum.model.dto.problem;

import com.hoctuan.codingforum.common.BaseRequestDTO;
import com.hoctuan.codingforum.constant.ProblemSubmissionLanguageType;
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
public class ProblemSubmissionRequestDTO extends BaseRequestDTO {
    private UserRequestDTO user;

    private String code;

    private ProblemSubmissionLanguageType languageType;
}
