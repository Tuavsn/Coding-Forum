package com.hoctuan.codingforum.service.problem.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.constant.GlobalVariables;
import com.hoctuan.codingforum.constant.SubmissionConfigurations;
import com.hoctuan.codingforum.constant.SubmitType;
import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.entity.problem.ProblemSubmission;
import com.hoctuan.codingforum.model.entity.problem.SubmissionResult;
import com.hoctuan.codingforum.service.problem.Judge0Service;
import com.hoctuan.codingforum.service.problem.Judge0SubmitFactory;
import com.hoctuan.codingforum.service.problem.Judge0SubmitStrategy;
import com.hoctuan.codingforum.service.rest.RestTemplateService;

@Service
public class Judge0ServiceImpl implements Judge0Service {
    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private Judge0SubmitFactory judge0SubmitFactory;

    @Override
    public List<SubmissionResult> submitSolution(List<Judge0RequestDTO> solutions, SubmitType type, ProblemSubmission problemSubmission) {
        Judge0SubmitStrategy strategy = judge0SubmitFactory.getSubmitStrategy(type);

        Map<String, String> judge0Params = getJudge0Params();

        return strategy.submitSolution(solutions, judge0Params, problemSubmission);
    }

    @Override
    public SubmissionResult runSolution(Judge0RequestDTO solution, SubmitType type) {
        Judge0SubmitStrategy strategy = judge0SubmitFactory.getSubmitStrategy(type);

        Map<String, String> judge0Params = getJudge0Params();

        return strategy.runSolution(solution, judge0Params);
    }

    @Override
    public void deleteSubmitReuslt(String token) {
        restTemplateService.delete(
            GlobalVariables.JUDGE0_URL,
            Map.of(SubmissionConfigurations.TOKEN,
            SubmissionConfigurations.AUTH_TOKEN)
        );
    }

    private Map<String, String> getJudge0Params() {
        Map<String, String> submitParam = Map.of(
            SubmissionConfigurations.TOKEN, SubmissionConfigurations.AUTH_TOKEN,
            SubmissionConfigurations.RETURN_FIELD, SubmissionConfigurations.FIELD_LIST
        );
        return submitParam;
    }
}
