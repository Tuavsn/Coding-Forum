package com.hoctuan.codingforum.service.problem.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.constant.GlobalVariables;
import com.hoctuan.codingforum.constant.Judge0Endpoint;
import com.hoctuan.codingforum.constant.SubmissionConfigurations;
import com.hoctuan.codingforum.model.dto.problem.Judge0BatchRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0BatchResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0ResponseDTO;
import com.hoctuan.codingforum.service.problem.Judge0Service;
import com.hoctuan.codingforum.service.rest.RestTemplateService;

@Service
public class Judge0ServiceImpl implements Judge0Service {
    @Autowired
    private RestTemplateService restTemplateService;

    @Override
    public Judge0ResponseDTO submitSolution(List<Judge0RequestDTO> solutions, Map<String, String> params) {
        params.put(SubmissionConfigurations.TOKEN, SubmissionConfigurations.AUTH_TOKEN);

        Judge0BatchRequestDTO batchRequest = new Judge0BatchRequestDTO(solutions);

        ParameterizedTypeReference<List<Judge0BatchResponseDTO.Token>> typeRef = new ParameterizedTypeReference<List<Judge0BatchResponseDTO.Token>>() {};

        List<Judge0BatchResponseDTO.Token> batchResults = restTemplateService.post(
            Judge0Endpoint.SUBMISSION_BATCH_ENDPOINT,
            batchRequest,
            params,
            typeRef);

        List<String> tokens = batchResults.stream().map(result -> result.getToken()).collect(Collectors.toList());
        
        return getSubmitResult(tokens, new HashMap<>());
    }

    @Override
    public Judge0ResponseDTO getSubmitResult(List<String> tokens, Map<String, String> params) {
        params.put(SubmissionConfigurations.TOKEN, SubmissionConfigurations.AUTH_TOKEN);

        params.put(SubmissionConfigurations.SUBMISSION_TOKEN, String.join(",", tokens));

        List<String> returnFields = List.of("token","stdout","stderr","status_id","language_id", "time", "memory");

        params.put(SubmissionConfigurations.RETURN_FIELD, String.join(",", returnFields));

        ParameterizedTypeReference<Judge0ResponseDTO> typeRef = new ParameterizedTypeReference<Judge0ResponseDTO>() {};

        return restTemplateService.get(
            Judge0Endpoint.SUBMISSION_BATCH_ENDPOINT,
            params,
            typeRef);
    };

    @Override
    public void deleteSubmitReuslt(String token) {
        restTemplateService.delete(
            GlobalVariables.JUDGE0_URL,
            Map.of(SubmissionConfigurations.TOKEN,
            SubmissionConfigurations.AUTH_TOKEN)
        );
    }
}
