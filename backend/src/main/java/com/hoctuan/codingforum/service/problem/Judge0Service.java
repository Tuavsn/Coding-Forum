package com.hoctuan.codingforum.service.problem;

import java.util.List;
import java.util.Map;

import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0ResponseDTO;

public interface Judge0Service {
    public Judge0ResponseDTO submitSolution(List<Judge0RequestDTO> solutions, Map<String, String> params);

    public Judge0ResponseDTO getSubmitResult(List<String> tokens, Map<String, String> params);

    public void deleteSubmitReuslt(String token);
}