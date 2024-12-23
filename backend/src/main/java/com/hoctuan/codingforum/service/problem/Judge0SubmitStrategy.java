package com.hoctuan.codingforum.service.problem;

import java.util.List;
import java.util.Map;

import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.entity.problem.ProblemSubmission;
import com.hoctuan.codingforum.model.entity.problem.SubmissionResult;

public interface Judge0SubmitStrategy {
    List<SubmissionResult> submitSolution(List<Judge0RequestDTO> solutions, Map<String, String> params, ProblemSubmission problemSubmission);

    SubmissionResult runSolution(Judge0RequestDTO solution, Map<String, String> params);
}
