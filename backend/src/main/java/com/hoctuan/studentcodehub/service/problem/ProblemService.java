package com.hoctuan.studentcodehub.service.problem;

import com.hoctuan.studentcodehub.common.BaseService;
import com.hoctuan.studentcodehub.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.studentcodehub.model.dto.problem.ProblemResponseDTO;

import java.util.UUID;

public interface ProblemService extends BaseService<ProblemResponseDTO, ProblemRequestDTO, UUID> {
}
