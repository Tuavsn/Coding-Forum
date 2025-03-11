package com.hoctuan.codingforum.service.problem.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.constant.Judge0Endpoint;
import com.hoctuan.codingforum.constant.ProblemResult;
import com.hoctuan.codingforum.constant.SubmissionConfigurations;
import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0ResponseDTO;
import com.hoctuan.codingforum.model.entity.problem.ProblemSubmission;
import com.hoctuan.codingforum.model.entity.problem.SubmissionResult;
import com.hoctuan.codingforum.repository.problem.SubmissionResultRepository;
import com.hoctuan.codingforum.service.problem.Judge0SubmitStrategy;
import com.hoctuan.codingforum.service.rest.RestTemplateService;

import jakarta.transaction.Transactional;

@Service
public class AsynchronousSubmitStrategy implements Judge0SubmitStrategy {
    private final RestTemplateService restTemplateService;
    private final SubmissionResultRepository submissionResultRepository;

    public AsynchronousSubmitStrategy(RestTemplateService restTemplateService,
            SubmissionResultRepository submissionResultRepository) {
        this.restTemplateService = restTemplateService;
        this.submissionResultRepository = submissionResultRepository;
    }

    @Override
    @Transactional
    public List<SubmissionResult> submitSolution(List<Judge0RequestDTO> solutions, Map<String, String> params,
            ProblemSubmission problemSubmission) {
        Map<String, String> submitParams = new HashMap<>();

        submitParams.putAll(params);

        submitParams.put(SubmissionConfigurations.CALLBACK_URL, Judge0Endpoint.CALLBACK_ENDPOINT);

        ParameterizedTypeReference<Judge0ResponseDTO> typeRef = new ParameterizedTypeReference<Judge0ResponseDTO>() {
        };

        List<Judge0ResponseDTO> submitResults = solutions.stream().map(solution -> restTemplateService.post(
                Judge0Endpoint.SUBMISSION_ENDPOINT,
                solution,
                submitParams,
                typeRef)).collect(Collectors.toList());

        List<SubmissionResult> submissionResults = submissionResultRepository.saveAll(
                IntStream.range(0, submitResults.size())
                        .mapToObj(i -> {
                            var result = submitResults.get(i);
                            return SubmissionResult.builder()
                                    .testCaseNum(i + 1) // Thứ tự bắt đầu từ 1
                                    .submitToken(result.getToken())
                                    .submitResult(ProblemResult.IN_QUEUE.getDisplayName())
                                    .submitError("")
                                    .stdout("")
                                    .time(0)
                                    .memory(0)
                                    .problemSubmission(problemSubmission)
                                    .build();
                        })
                        .collect(Collectors.toList()));

        return submissionResults;
    }

    @Override
    public SubmissionResult runSolution(Judge0RequestDTO solution, Map<String, String> params) {
        throw new UnsupportedOperationException("This method is not supported in AsynchronousSubmitStrategy");
    }
}
