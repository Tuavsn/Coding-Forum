package com.hoctuan.codingforum.service.problem.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.common.Utils;
import com.hoctuan.codingforum.constant.ProblemResult;
import com.hoctuan.codingforum.exception.NotFoundException;
import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0ResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionResponseDTO;
import com.hoctuan.codingforum.model.entity.problem.Problem;
import com.hoctuan.codingforum.model.mapper.ProblemMapper;
import com.hoctuan.codingforum.repository.problem.ProblemRepository;
import com.hoctuan.codingforum.repository.problem.ProblemSubmissionRepository;
import com.hoctuan.codingforum.repository.problem.SubmissionResultRepository;
import com.hoctuan.codingforum.service.problem.Judge0Service;
import com.hoctuan.codingforum.service.problem.ProblemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl extends BaseServiceImpl<
        Problem,
        ProblemResponseDTO,
        ProblemRequestDTO,
        UUID> implements ProblemService {
    private static final Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);
    
    private final ProblemRepository problemRepository;
    private final ProblemMapper problemMapper;
    @Autowired
    private Judge0Service judge0Service;
    @Autowired
    private ProblemSubmissionRepository problemSubmissionRepository;
    @Autowired
    private SubmissionResultRepository submissionResultRepository;

    public ProblemServiceImpl(ProblemRepository problemRepository, ProblemMapper problemMapper) {
        super(problemRepository, problemMapper);
        this.problemRepository = problemRepository;
        this.problemMapper = problemMapper;
    }

    @Override
    public ProblemSubmissionResponseDTO submitSolution(UUID id, ProblemSubmissionRequestDTO solutions,
            Map<String, String> params) {
        Problem existedProblem = problemRepository.findById(id).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));

        List<Judge0RequestDTO> judge0RequestDTOs = new ArrayList<Judge0RequestDTO>();

        Utils.splitStringByPipe(existedProblem.getTestCases()).forEach(testCase -> {
            List<String> separatedStringBySemicolon = Utils.splitStringBySemicolon(testCase);

            String input = formatInput(separatedStringBySemicolon.get(0));

            String output = separatedStringBySemicolon.get(1);

            judge0RequestDTOs.add(
                Judge0RequestDTO.builder()
                    .source_code(solutions.getCode())
                    .language_id(solutions.getLanguageType().getCode())
                    .stdin(input)
                    .expected_output(output)
                    .build()
            );
        }); 

        Judge0ResponseDTO results = judge0Service.submitSolution(judge0RequestDTOs, params);

        boolean isNotAccept = results.getSubmissions().stream().anyMatch(result -> ProblemResult.ACCEPTED.getCode() != result.getStatus_id());

        return ProblemSubmissionResponseDTO.builder()
            .code(solutions.getCode())
            .languageType(solutions.getLanguageType())
            .result(isNotAccept ? "Wrong Answer" : "Accepted")
            .build();
    }

    @Override
    public Page<ProblemSubmissionResponseDTO> getSubmitResult(Pageable pageable, MultiValueMap<String, String> params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSubmitResult'");
    }

    @Override
    public void deleteSubmitReuslt(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteSubmitReuslt'");
    }

    private String formatInput(String input) {
        List<String> result = Utils.splitStringByComma(input);

        result = result.stream()
            .map(string -> Utils.separateDigitsWithSpace(string)) // Sửa: trả về kết quả từ phương thức
            .collect(Collectors.toList());
        
        return Utils.joinListWithNewLine(result);
    }
}
