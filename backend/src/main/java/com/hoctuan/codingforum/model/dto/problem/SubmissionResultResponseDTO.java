package com.hoctuan.codingforum.model.dto.problem;

import com.hoctuan.codingforum.common.BaseResponseDTO;

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
public class SubmissionResultResponseDTO  extends BaseResponseDTO {
    private int testCaseNum;

    private String submitResult;

    private String submitToken;

    private String submitError;

    private String stdout;

    private double time;

    private double memory;
}
