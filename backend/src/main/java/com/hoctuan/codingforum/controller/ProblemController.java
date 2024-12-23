package com.hoctuan.codingforum.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hoctuan.codingforum.common.BaseController;
import com.hoctuan.codingforum.common.BaseResponse;
import com.hoctuan.codingforum.constant.SubmitType;
import com.hoctuan.codingforum.model.dto.problem.Judge0ResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.SubmissionResultResponseDTO;
import com.hoctuan.codingforum.model.entity.problem.Problem;
import com.hoctuan.codingforum.service.problem.ProblemService;

import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("api/problem")
public class ProblemController extends BaseController<
        Problem,
        ProblemResponseDTO,
        ProblemRequestDTO,
        UUID> {
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        super(problemService);
        this.problemService = problemService;
    }

    @Override
    public ResponseEntity<BaseResponse> findAll(
        @ParameterObject Pageable pageable,
        @RequestParam(defaultValue = "") String search
    ) {
        return super.findAll(pageable, search);
    }

    @Override
    public ResponseEntity<BaseResponse> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody ProblemRequestDTO DTO) {
        return super.create(DTO);
    }

    @Override
    public ResponseEntity<BaseResponse> update(
        @PathVariable UUID id,
        @Valid @RequestBody ProblemRequestDTO DTO
    ) {
        return super.update(id, DTO);
    }

    @Override
    public ResponseEntity<BaseResponse> delete(@PathVariable UUID id) {
        return super.delete(id);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<BaseResponse> submitSolution(
        @PathVariable UUID id,
        @Valid @RequestBody ProblemSubmissionRequestDTO DTO,
        @RequestParam String type
    ) {
        ProblemSubmissionResponseDTO data = problemService.submitSolution(id, DTO, SubmitType.getByName(type));
        return new ResponseEntity<>(
                BaseResponse.builder()
                            .message("Submit thành công")
                            .data(data)
                            .status(HttpStatus.OK.value())
                            .build()
                        , HttpStatus.OK);
    }

    @PostMapping("/{id}/run")
    public ResponseEntity<BaseResponse> runSolution(
        @PathVariable UUID id,
        @Valid @RequestBody ProblemSubmissionRequestDTO DTO,
        @RequestParam String type
    ) {
        SubmissionResultResponseDTO data = problemService.runSolution(id, DTO, SubmitType.getByName(type));
        return new ResponseEntity<>(
                BaseResponse.builder()
                            .message("Run thành công")
                            .data(data)
                            .status(HttpStatus.OK.value())
                            .build()
                        , HttpStatus.OK);
    }

    @GetMapping("/{id}/submissions")
    public ResponseEntity<BaseResponse> getSubmissionResult(
        @PathVariable UUID id,
        @ParameterObject Pageable pageable,
        @RequestParam MultiValueMap<String, String> params
    ) {
        Page<ProblemSubmissionResponseDTO> data = problemService.getSubmissions(id, pageable);
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Lấy submission thành công")
                        .data(data)
                        .status(HttpStatus.OK.value())
                        .build()
                    , HttpStatus.OK);
    }

    @GetMapping("/submissions/{submissionId}")
    public ResponseEntity<BaseResponse> getSubmissionResultById(
        @PathVariable UUID submissionId
    ) {
        ProblemSubmissionResponseDTO data = problemService.getSubmitResult(submissionId);
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Lấy submission thành công")
                        .data(data)
                        .status(HttpStatus.OK.value())
                        .build()
                    , HttpStatus.OK);
    }

    @PutMapping("/update-judge0-result")
    public void getSubmissionResultById(
        @RequestBody Judge0ResponseDTO response
    ) {
        System.out.println(">>>> response from judge0" + response.toString());
    }

    // @GetMapping("/{id}/delete-submissions")
    // public ResponseEntity<BaseResponse> deleteSubmissionResult(
    //     @PathVariable UUID id,
    //     @RequestParam MultiValueMap<String, String> params
    // ) {
    //     return new ResponseEntity<>(
    //             BaseResponse.builder()
    //                     .message("Xóa submission thành công")
    //                     .data(null)
    //                     .status(HttpStatus.OK.value())
    //                     .build()
    //             , HttpStatus.OK);
    // }
}
