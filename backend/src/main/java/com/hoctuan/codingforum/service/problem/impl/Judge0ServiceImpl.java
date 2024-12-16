package com.hoctuan.codingforum.service.problem.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hoctuan.codingforum.constant.GlobalVariables;
import com.hoctuan.codingforum.constant.Judge0Endpoint;
import com.hoctuan.codingforum.constant.SubmissionConfigurations;
import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0ResponseDTO;
import com.hoctuan.codingforum.service.problem.Judge0Service;
import com.hoctuan.codingforum.service.problem.SubmissionStrategy;
import com.hoctuan.codingforum.service.rest.RestTemplateService;

@Service
public class Judge0ServiceImpl implements Judge0Service {
    @Autowired
    private Judge0SubmitFactory judge0SubmitFactory;
    @Autowired
    private RestTemplateService restTemplateService;

    @Override
    public List<Judge0ResponseDTO> submitSolution(List<Judge0RequestDTO> solutions, Map<String, String> params) {
        params.put(SubmissionConfigurations.TOKEN, SubmissionConfigurations.AUTH_TOKEN);
        boolean isWaitingForResult;
        if(ObjectUtils.isEmpty(params.get(SubmissionConfigurations.RESULT_WAIT))) {
            isWaitingForResult = true;
        } else {
            isWaitingForResult = false;
        }
        SubmissionStrategy strategy = judge0SubmitFactory.getSubmissionStrategy(isWaitingForResult);
        return strategy.submitSolution(solutions, params);
    }

    @Override
    public List<Judge0ResponseDTO> getSubmitResult(List<String> tokens, Map<String, String> params) {
        params.put(SubmissionConfigurations.TOKEN, SubmissionConfigurations.AUTH_TOKEN);
        List<Object> result = restTemplateService.get(
            Judge0Endpoint.SUBMISSION_BATCH_ENDPOINT,
            params,
            List.class);
        return result.stream().map(res -> 
            (Judge0ResponseDTO) res
        ).collect(Collectors.toList());
    }

    @Override
    public void deleteSubmitReuslt(String token) {
        restTemplateService.delete(
            GlobalVariables.JUDGE0_URL,
            Map.of(SubmissionConfigurations.TOKEN,
            SubmissionConfigurations.AUTH_TOKEN)
        );
    }
}
