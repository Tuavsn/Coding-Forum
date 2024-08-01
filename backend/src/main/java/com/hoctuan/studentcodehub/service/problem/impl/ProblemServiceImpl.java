package com.hoctuan.studentcodehub.service.problem.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.studentcodehub.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.studentcodehub.model.entity.problem.Problem;
import com.hoctuan.studentcodehub.service.problem.ProblemService;

import java.util.UUID;

public class ProblemServiceImpl extends BaseServiceImpl<
        Problem,
        ProblemResponseDTO,
        ProblemRequestDTO,
        UUID> implements ProblemService {
}
