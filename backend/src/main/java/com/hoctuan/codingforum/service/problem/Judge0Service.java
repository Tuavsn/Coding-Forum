package com.hoctuan.codingforum.service.problem;

import java.util.List;

import com.hoctuan.codingforum.constant.SubmitType;
import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.entity.problem.ProblemSubmission;
import com.hoctuan.codingforum.model.entity.problem.SubmissionResult;

public interface Judge0Service {
    public List<SubmissionResult> submitSolution(List<Judge0RequestDTO> solutions, SubmitType type, ProblemSubmission problemSubmission);

    public SubmissionResult runSolution(Judge0RequestDTO solution, SubmitType type);

    // public Judge0ResponseDTO getSubmitResult(List<String> tokens, Map<String, String> params);

    public void deleteSubmitReuslt(String token);
}