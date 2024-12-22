package com.hoctuan.codingforum.model.dto.problem;

import java.util.Set;

import com.hoctuan.codingforum.common.BaseResponseDTO;
import com.hoctuan.codingforum.constant.ProblemSubmissionLanguageType;

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
public class ProblemSubmissionResponseDTO extends BaseResponseDTO {
    private Set<SubmissionResultResponseDTO> submissionResults;

    private String code;

    private ProblemSubmissionLanguageType languageType;

    private double time;

    private String result;

    private double score;
}
