package com.hoctuan.codingforum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoctuan.codingforum.common.BaseController;
import com.hoctuan.codingforum.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.codingforum.model.entity.problem.Problem;
import com.hoctuan.codingforum.service.problem.ProblemService;

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
}
