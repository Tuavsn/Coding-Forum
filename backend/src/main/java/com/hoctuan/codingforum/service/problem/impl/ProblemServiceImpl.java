package com.hoctuan.codingforum.service.problem.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.common.Utils;
import com.hoctuan.codingforum.constant.ProblemResult;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.exception.NotFoundException;
import com.hoctuan.codingforum.model.dto.problem.Judge0RequestDTO;
import com.hoctuan.codingforum.model.dto.problem.Judge0ResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionResponseDTO;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.problem.Problem;
import com.hoctuan.codingforum.model.entity.problem.ProblemSubmission;
import com.hoctuan.codingforum.model.entity.problem.SubmissionResult;
import com.hoctuan.codingforum.model.mapper.ProblemMapper;
import com.hoctuan.codingforum.model.mapper.ProblemSubmissonMapper;
import com.hoctuan.codingforum.repository.problem.ProblemRepository;
import com.hoctuan.codingforum.repository.problem.ProblemSubmissionRepository;
import com.hoctuan.codingforum.repository.problem.SubmissionResultRepository;
import com.hoctuan.codingforum.service.common.AuthContext;
import com.hoctuan.codingforum.service.problem.Judge0Service;
import com.hoctuan.codingforum.service.problem.ProblemService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private SubmissionResultRepository submissionResultRepository;
    @Autowired
    private ProblemSubmissonMapper problemSubmissonMapper;

    public ProblemServiceImpl(ProblemRepository problemRepository, ProblemMapper problemMapper) {
        super(problemRepository, problemMapper);
        this.problemRepository = problemRepository;
        this.problemMapper = problemMapper;
    }

    @Override
    @Transactional
    public ProblemSubmissionResponseDTO submitSolution(UUID id, ProblemSubmissionRequestDTO solutions,
            Map<String, String> params) {
        Problem existedProblem = problemRepository.findById(id).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));

        User submitUser = authContext.getUserAuthenticated();

        List<Judge0RequestDTO> judge0RequestDTOs = getSubmitRequest(existedProblem, solutions);

        Judge0ResponseDTO results = judge0Service.submitSolution(judge0RequestDTOs, params);

        List<SubmissionResult> submissionResults = submissionResultRepository.saveAll(
            IntStream.range(0, results.getSubmissions().size())
                .mapToObj(i -> {
                    var result = results.getSubmissions().get(i);
                    return SubmissionResult.builder()
                            .testCaseNum(i + 1) // Thứ tự bắt đầu từ 1
                            .submitToken(result.getToken())
                            .submitResult(ProblemResult.getDisplayNameByCode(result.getStatus_id()))
                            .submitError(result.getStderr())
                            .build();
                })
            .collect(Collectors.toList())
        );

        ProblemSubmission savedProblemSubmission = problemSubmissionRepository.save(
            ProblemSubmission.builder()
                .problem(existedProblem)
                .code(solutions.getCode())
                .languageType(solutions.getLanguageType())
                .user(submitUser)
                .submissionResults(new HashSet<>(submissionResults))
                .result("Pending")
                .score(0)
                .build()
        );

        // asyncUpdateBatchResult(
        //     results.getSubmissions().stream().map(result -> result.getToken()).collect(Collectors.toList()),
        //     savedProblemSubmission
        // );

        return problemSubmissonMapper.toDTO(savedProblemSubmission);
    }

    @Override
    public ProblemSubmissionResponseDTO getSubmitResult(UUID problemSubmissionId, MultiValueMap<String, String> params) {
        ProblemSubmission existedProblemSubmission = problemSubmissionRepository.findById(problemSubmissionId).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));
        return problemSubmissonMapper.toDTO(existedProblemSubmission);
    }

    @Override
    public Page<ProblemSubmissionResponseDTO> getSubmissions(Pageable pageable, UUID id) {
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

    @Async
    public void asyncUpdateBatchResult(List<String> tokens, ProblemSubmission problemSubmission) {
        Map<String, String> params = new HashMap<>();
        boolean isCompleted = false;

        // Ánh xạ tokens với SubmissionResult
        Map<String, SubmissionResult> tokenToResultMap = problemSubmission.getSubmissionResults().stream()
                .collect(Collectors.toMap(SubmissionResult::getSubmitToken, result -> result));

        int totalTestCases = tokens.size();
        int passedTestCases = 0; // Đếm số test case Passed

        while (!isCompleted) {
            Judge0ResponseDTO results = judge0Service.getSubmitResult(tokens, params);
            isCompleted = true;

            for (int i = 0; i < tokens.size(); i++) {
                String token = tokens.get(i);
                Judge0ResponseDTO.Submission submission = results.getSubmissions().get(i);

                // Lấy SubmissionResult từ map
                SubmissionResult submissionResult = tokenToResultMap.get(token);

                if (submissionResult != null) {
                    // Cập nhật thông tin vào SubmissionResult
                    submissionResult.setSubmitResult(submission.getStdout());
                    submissionResult.setSubmitError(submission.getStderr());
                    submissionResultRepository.save(submissionResult); // Lưu kết quả mới vào DB

                    // Nếu submission được chấp nhận (status_id == 3), tăng số test case Passed
                    if (ProblemResult.ACCEPTED.getCode() == submission.getStatus_id()) { // Status "Accepted"
                        passedTestCases++;
                    }

                    // Nếu còn trạng thái "in_queue" hoặc "processing", tiếp tục kiểm tra
                    if (ProblemResult.IN_QUEUE.getCode() == submission.getStatus_id() || ProblemResult.PROCESSING.getCode() == submission.getStatus_id()) {
                        isCompleted = false;
                    }
                } else {
                    throw new RuntimeException("Token không tồn tại trong SubmissionResult");
                }
            }

            // Tạm dừng trước khi gọi lại (throttling API)
            if (!isCompleted) {
                try {
                    Thread.sleep(2000); // Đợi 2 giây trước khi gọi lại
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted", e);
                }
            }
        }

        // Khi hoàn tất, tính điểm và cập nhật ProblemSubmission
        double score = (double) passedTestCases / totalTestCases * 100.0;
        String finalResult = passedTestCases == totalTestCases ? "Accepted" : "Wrong Answer";

        problemSubmission.setResult(finalResult);
        problemSubmission.setScore(score);
        problemSubmissionRepository.save(problemSubmission); // Lưu kết quả vào DB
    }
}
