package com.hoctuan.studentcodehub.controller;

import com.hoctuan.studentcodehub.common.BaseController;
import com.hoctuan.studentcodehub.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.studentcodehub.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.studentcodehub.model.entity.problem.Problem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/problem")
public class ProblemController extends BaseController<
        Problem,
        ProblemResponseDTO,
        ProblemRequestDTO,
        UUID> {
}
