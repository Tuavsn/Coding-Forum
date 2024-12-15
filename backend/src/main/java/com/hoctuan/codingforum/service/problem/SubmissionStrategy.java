package com.hoctuan.codingforum.service.problem;

import java.util.List;
import java.util.Map;

import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0ResponseDTO;

public interface SubmissionStrategy {
    List<Judge0ResponseDTO> submitSolution(List<Judge0RequestDTO> solution, Map<String, String> params);
}
