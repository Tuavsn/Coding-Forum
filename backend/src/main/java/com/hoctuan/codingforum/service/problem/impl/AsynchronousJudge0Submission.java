package com.hoctuan.codingforum.service.problem.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.constant.Judge0Endpoint;
import com.hoctuan.codingforum.model.dto.problem.Judge0BatchRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0ResponseDTO;
import com.hoctuan.codingforum.service.problem.SubmissionStrategy;
import com.hoctuan.codingforum.service.rest.RestTemplateService;

@Component
public class AsynchronousJudge0Submission implements SubmissionStrategy {
@Autowired
    private RestTemplateService restTemplateService;

    @Override
    public List<Judge0ResponseDTO> submitSolution(List<Judge0RequestDTO> solution, Map<String, String> params) {
        Judge0BatchRequestDTO batchRequest = new Judge0BatchRequestDTO(solution);
        List<Object> result = restTemplateService.post(
            Judge0Endpoint.SUBMISSION_BATCH_ENDPOINT,
            batchRequest,
            params,
            List.class);
        return result.stream().map(res -> 
            (Judge0ResponseDTO) res
        ).collect(Collectors.toList());
    }
}
