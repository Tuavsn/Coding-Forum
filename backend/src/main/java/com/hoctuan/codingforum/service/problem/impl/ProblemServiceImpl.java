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
import com.hoctuan.codingforum.constant.AccountRole;
import com.hoctuan.codingforum.constant.ErrorCode;
import com.hoctuan.codingforum.constant.ProblemResult;
import com.hoctuan.codingforum.constant.SubmitType;
import com.hoctuan.codingforum.exception.CustomException;
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
import com.hoctuan.codingforum.repository.account.UserRepository;
import com.hoctuan.codingforum.repository.problem.ProblemRepository;
import com.hoctuan.codingforum.repository.problem.ProblemSubmissionRepository;
import com.hoctuan.codingforum.service.common.AuthContext;
import com.hoctuan.codingforum.service.problem.Judge0Service;
import com.hoctuan.codingforum.service.problem.ProblemService;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl extends BaseServiceImpl<Problem, ProblemResponseDTO, ProblemRequestDTO, UUID>
        implements ProblemService {
    private static final Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);
    private final ProblemRepository problemRepository;
    private final ProblemMapper problemMapper;
    private final Judge0Service judge0Service;
    private final ProblemSubmissionRepository problemSubmissionRepository;
    private final UserRepository userRepository;
    private final AuthContext authContext;
    private final ProblemSubmissonMapper problemSubmissonMapper;

    public ProblemServiceImpl(ProblemRepository problemRepository, ProblemMapper problemMapper,
            Judge0Service judge0Service,
            ProblemSubmissionRepository problemSubmissionRepository, UserRepository userRepository,
            AuthContext authContext,
            ProblemSubmissonMapper problemSubmissonMapper) {
        super(problemRepository, problemMapper, Problem.class);
        this.problemRepository = problemRepository;
        this.problemMapper = problemMapper;
        this.judge0Service = judge0Service;
        this.problemSubmissionRepository = problemSubmissionRepository;
        this.userRepository = userRepository;
        this.authContext = authContext;
        this.problemSubmissonMapper = problemSubmissonMapper;
    }

    /**
     * Save new Problem
     * 
     * @param dto
     * @return New Problem
     * @throws BadRequest if invalid permission request
     */
    @Override
    @Transactional
    public ProblemResponseDTO save(ProblemRequestDTO dto) {
        User user = authContext.getCurrentUserEntityLogin();
        if (dto.getId() != null) {
            Problem existedProblem = getExistedProblem(dto.getId());
            // check if user is problem author
            checkAuthorPermission(existedProblem, user);
            // check if new thumbnail
            if (dto.getThumbnail().isEmpty()) {
                dto.setThumbnail(existedProblem.getThumbnail());
            }
        }
        UserRequestDTO userDTO = UserRequestDTO.builder().id(user.getId()).build();
        dto.setAuthor(userDTO);
        return super.save(dto);
    }

    // *Todo: update function

    /**
     * Submit solution
     * 
     * @param id        Problem's id
     * @param solutions User solution
     * @param type      [Synchronous] or [Asynchronous]
     * @return Submit result
     * @throws NotFound if existed Problem or existed User not found
     */
    @Override
    @Transactional
    public ProblemSubmissionResponseDTO submitSolution(UUID id, ProblemSubmissionRequestDTO solutions,
            SubmitType type) {
        Problem existedProblem = getExistedProblem(id);
        User submitUser = authContext.getCurrentUserEntityLogin();
        List<Judge0RequestDTO> judge0RequestDTOs = getSubmitRequest(existedProblem, solutions);
        ProblemSubmission savedProblemSubmission = problemSubmissionRepository.save(
                ProblemSubmission.builder()
                        .problem(existedProblem)
                        .code(solutions.getCode())
                        .languageType(solutions.getLanguageType())
                        .user(submitUser)
                        .result(ProblemResult.PROCESSING.getDisplayName())
                        .time(0)
                        .memory(0)
                        .score(0)
                        .build());
        List<SubmissionResult> submissionResults = judge0Service.submitSolution(judge0RequestDTOs, type,
                savedProblemSubmission);
        return problemSubmissonMapper.toDTO(
                saveProblemSubmissionResult(
                        savedProblemSubmission,
                        existedProblem,
                        submissionResults,
                        submitUser));
    }

    /**
     * Run solution
     * 
     * @param id        Problem's id
     * @param solutions User solution
     * @param type      [Synchronous] or [Asynchronous]
     * @return Run result
     * @throws NotFound if existed Problem not found
     */
    @Override
    public SubmissionResultResponseDTO runSolution(UUID id, ProblemSubmissionRequestDTO solutions, SubmitType type) {
        Problem existedProblem = getExistedProblem(id);
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

    /**
     * Get a valid Judge0's request from Problem's inline string testcase
     * Example:
     * Sum of all elements in array
     * Problem's testcase [5,1 2 3 4 5;15|3,1 2 3;6]
     * Valid request after convert from testcase:
     * - Request 1:
     * + Input: [5\n1 2 3 4 5]
     * + Expected ouput: 15
     * - Request 2:
     * + Input: [3\n1 2 3]
     * + Output: 6
     * 
     * @param problem
     * @param solutions
     * @return List of Judge0's request
     * @throws Badrequest if invalid testcase
     */
    private List<Judge0RequestDTO> getSubmitRequest(Problem problem, ProblemSubmissionRequestDTO solutions) {
        return Utils.splitStringByPipe(problem.getTestCases()).stream().map(testCase -> {
            List<String> separatedStringBySemicolon = Utils.splitStringBySemicolon(testCase);
            if (separatedStringBySemicolon.size() < 2) {
                throw new CustomException(ErrorCode.INVALID_TEST_CASE);
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

    /**
     * Convert from inline string to valid Judge0 argument input
     * Example: 5,1 2 3 4 5 -> 5\n1 2 3 4 5
     * 
     * @param input
     * @return valid input string
     */
    private String formatInput(String input) {
        List<String> stringSplitByComma = Utils.splitStringByComma(input);
        List<String> result = Utils.splitStringBySpace(stringSplitByComma);
        return Utils.joinListWithNewLine(result);
    }

    /**
     * caclculate result
     * 
     * @param problemSubmission
     * @param problem
     * @param submissionResults
     * @param submitUser
     * @return calculated result
     */
    @Transactional
    private ProblemSubmission saveProblemSubmissionResult(
            ProblemSubmission problemSubmission,
            Problem problem,
            List<SubmissionResult> submissionResults,
            User submitUser) {
        problemSubmission.setSubmissionResults(new HashSet<>(submissionResults));
        problem.getProblemSubmissions().add(problemSubmission);
        double totalTestCases = submissionResults.size();
        long passedTestCases = submissionResults.stream()
                .filter(result -> ProblemResult.ACCEPTED.getDisplayName().equals(result.getSubmitResult()))
                .count();
        double totalTime = 0;
        double totalMemory = 0;
        for (SubmissionResult submissionResult : submissionResults) {
            totalTime += submissionResult.getTime();
            totalMemory += submissionResult.getMemory();
        }
        double avarageTime = 0;
        double avarageMemory = 0;
        if (totalTime > 0)
            avarageTime = (totalTime / totalTestCases);
        if (totalMemory > 0)
            avarageMemory = (totalMemory / totalTestCases);
        double score = (passedTestCases / totalTestCases) * problem.getTotalScore();
        problemSubmission.setScore(score);
        problemSubmission.setTime(avarageTime);
        problemSubmission.setMemory(avarageMemory);
        if (passedTestCases == totalTestCases) {
            problemSubmission.setResult(ProblemResult.ACCEPTED.getDisplayName());
        } else {
            problemSubmission.setResult(ProblemResult.WRONG_ANSWER.getDisplayName());
        }
        problem.getProblemSubmissions().add(problemSubmission);
        problemRepository.save(problem);
        updateUserPoint(problem, submitUser, score);
        return problemSubmission;
    }

    /**
     * Save User point
     * 
     * @param problem
     * @param submitUser
     * @param score
     */
    private void updateUserPoint(Problem problem, User submitUser, double score) {
        List<ProblemSubmission> existedSubmission = problemSubmissionRepository.findByProblemAndUser(problem,
                submitUser);
        if (existedSubmission.isEmpty()) { // Save new score
            submitUser.setTotalSubmissionPoint(submitUser.getTotalSubmissionPoint() + score);
        } else { // Save highest score
            submitUser.setTotalSubmissionPoint(existedSubmission
                    .stream()
                    .max(Comparator.comparingDouble(result -> result.getScore()))
                    .orElse(existedSubmission.get(0))
                    .getScore());
        }
        userRepository.save(submitUser);
    }

    /**
     * Get all Submission results
     * 
     * @param id
     * @param pageable
     * @return Page of submit results
     * @throws Notfound if existed Problem or existed User not found
     */
    @Override
    public Page<ProblemSubmissionResponseDTO> getSubmissions(UUID id, Pageable pageable) {
        User submitUser = authContext.getCurrentUserEntityLogin();
        Problem existedProblem = getExistedProblem(id);
        return problemSubmissionRepository.findByProblemAndUser(pageable, existedProblem, submitUser)
                .map(problemSubmissonMapper::toDTO);
    }

    /**
     * Get submit result by id
     * 
     * @param problemSubmissionId
     * @return Submit result
     * @throws Notfound if existed Problem not found
     */
    @Override
    public ProblemSubmissionResponseDTO getSubmitResult(UUID problemSubmissionId) {
        ProblemSubmission existedProblemSubmission = getExistedProblemSubmission(problemSubmissionId);
        return problemSubmissonMapper.toDTO(existedProblemSubmission);
    }

    private void checkAuthorPermission(Problem problem, User author) {
        if (problem.getAuthor().getId() != author.getId() && !author.getRole().equals(AccountRole.SYS_ADMIN)) {
            throw new CustomException(ErrorCode.WRONG_AUTHOR);
        }
    }

    private Problem getExistedProblem(UUID id) {
        return problemRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.PROBLEM_NOT_FOUND));
    }

    private ProblemSubmission getExistedProblemSubmission(UUID id) {
        return problemSubmissionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SUBMISSION_NOT_FOUND));
    }
}
