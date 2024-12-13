package com.hoctuan.codingforum.service.problem;

import java.util.List;
import java.util.Map;

import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;

public interface Judge0Service {
    public String submitSolution(List<Judge0RequestDTO> solutions, Map<String, ? extends Object> params);

    public String getSubmitResult(List<String> tokens, Map<String, ? extends Object> params);

    public void deleteSubmitReuslt(String token);
}