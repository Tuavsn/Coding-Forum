package com.hoctuan.codingforum.service.problem.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.common.Utils;
import com.hoctuan.codingforum.constant.ProblemResult;
import com.hoctuan.codingforum.constant.SubmitType;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.exception.NotFoundException;
import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.SubmissionResultResponseDTO;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.problem.Problem;
import com.hoctuan.codingforum.model.entity.problem.ProblemSubmission;
import com.hoctuan.codingforum.model.entity.problem.SubmissionResult;
import com.hoctuan.codingforum.model.mapper.ProblemMapper;
import com.hoctuan.codingforum.model.mapper.ProblemSubmissonMapper;
import com.hoctuan.codingforum.repository.problem.ProblemRepository;
import com.hoctuan.codingforum.repository.problem.ProblemSubmissionRepository;
import com.hoctuan.codingforum.service.common.AuthContext;
import com.hoctuan.codingforum.service.problem.Judge0Service;
import com.hoctuan.codingforum.service.problem.ProblemService;

import java.util.HashSet;
import java.util.List;
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
    private AuthContext authContext;
    @Autowired
    private ProblemSubmissonMapper problemSubmissonMapper;

    public ProblemServiceImpl(ProblemRepository problemRepository, ProblemMapper problemMapper) {
        super(problemRepository, problemMapper);
        this.problemRepository = problemRepository;
        this.problemMapper = problemMapper;
    }

    @Override
    @Transactional
    public ProblemResponseDTO save(ProblemRequestDTO dto) {
        User user = authContext.getUserAuthenticated();

        // check if user is problem author
        if(dto.getId() != null) {
            Problem existedProblem = problemRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));
            if(existedProblem.getAuthor().getId() != user.getId()) {
                throw new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value());
            }
        }

        UserRequestDTO userDTO = UserRequestDTO.builder().id(user.getId()).build();

        dto.setAuthor(userDTO);

        return super.forceSave(dto);
    }

    @Override
    @Transactional
    public ProblemSubmissionResponseDTO submitSolution(UUID id, ProblemSubmissionRequestDTO solutions, SubmitType type) {
        Problem existedProblem = problemRepository.findById(id).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));

        User submitUser = authContext.getUserAuthenticated();

        List<Judge0RequestDTO> judge0RequestDTOs = getSubmitRequest(existedProblem, solutions);

        ProblemSubmission savedProblemSubmission = problemSubmissionRepository.save(
            ProblemSubmission.builder()
                .problem(existedProblem)
                .code(solutions.getCode())
                .languageType(solutions.getLanguageType())
                .user(submitUser)
                .result(ProblemResult.PROCESSING.getDisplayName())
                .score(0)
                .build()
        );

        List<SubmissionResult> submissionResults = judge0Service.submitSolution(judge0RequestDTOs, type, savedProblemSubmission);

        savedProblemSubmission.setSubmissionResults(new HashSet<>(submissionResults));

        existedProblem.getProblemSubmissions().add(savedProblemSubmission);

        double totalTestCases = submissionResults.size();
        
        long passedTestCases = submissionResults.stream()
            .filter(result -> ProblemResult.ACCEPTED.getDisplayName().equals(result.getSubmitResult()))
            .count();
        
        double score = (passedTestCases / totalTestCases) * existedProblem.getTotalScore();
        savedProblemSubmission.setScore(score);

        if (passedTestCases == totalTestCases) {
            savedProblemSubmission.setResult(ProblemResult.ACCEPTED.getDisplayName());
        } else {
            savedProblemSubmission.setResult(ProblemResult.WRONG_ANSWER.getDisplayName());
        }

        existedProblem.getProblemSubmissions().add(savedProblemSubmission);

        problemRepository.save(existedProblem);

        return problemSubmissonMapper.toDTO(savedProblemSubmission);
    }

    @Override
    public SubmissionResultResponseDTO runSolution(UUID id, ProblemSubmissionRequestDTO solutions, SubmitType type) {
        Problem existedProblem = problemRepository.findById(id).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));

        List<Judge0RequestDTO> judge0RequestDTO = getSubmitRequest(existedProblem, solutions);

        SubmissionResult submissionResult = judge0Service.runSolution(judge0RequestDTO.get(0), type);

        return SubmissionResultResponseDTO.builder()
            .submitToken(submissionResult.getSubmitToken())
            .submitResult(submissionResult.getSubmitResult())
            .submitError(submissionResult.getSubmitError())
            .stdout(submissionResult.getStdout())
            .time(submissionResult.getTime())
            .memory(submissionResult.getMemory())
            .build();
    }

    @Override
    public ProblemSubmissionResponseDTO getSubmitResult(UUID problemSubmissionId) {
        ProblemSubmission existedProblemSubmission = problemSubmissionRepository.findById(problemSubmissionId).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));
        return problemSubmissonMapper.toDTO(existedProblemSubmission);
    }

    @Override
    public Page<ProblemSubmissionResponseDTO> getSubmissions(UUID id, Pageable pageable) {
        Problem existedProblem = problemRepository.findById(id).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));

        return problemSubmissionRepository.findByProblem(pageable, existedProblem).map(problemSubmissonMapper::toDTO);
    }

    @Override
    public void deleteSubmitReuslt(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteSubmitReuslt'");
    }

    private List<Judge0RequestDTO> getSubmitRequest(Problem problem, ProblemSubmissionRequestDTO solutions) {
        return Utils.splitStringByPipe(problem.getTestCases()).stream().map(testCase -> {
            List<String> separatedStringBySemicolon = Utils.splitStringBySemicolon(testCase);

            if(separatedStringBySemicolon.size() < 2) {
                throw new CustomException("Test case không hợp lệ", HttpStatus.BAD_REQUEST.value());
            }

            String input = formatInput(separatedStringBySemicolon.get(0));

            String output = separatedStringBySemicolon.get(1);

            return Judge0RequestDTO.builder()
                .source_code(solutions.getCode())
                .language_id(solutions.getLanguageType().getCode())
                .stdin(input)
                .expected_output(output)
                .build();
        }).collect(Collectors.toList()); 
    }

    private String formatInput(String input) {
        List<String> result = Utils.splitStringByComma(input);

        result = result.stream()
            .map(string -> Utils.separateDigitsWithSpace(string)) // Sửa: trả về kết quả từ phương thức
            .collect(Collectors.toList());
        
        return Utils.joinListWithNewLine(result);
    }
}
