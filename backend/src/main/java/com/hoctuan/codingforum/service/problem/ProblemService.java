package com.hoctuan.codingforum.service.problem;

import java.util.UUID;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemResponseDTO;

public interface ProblemService extends BaseService<ProblemResponseDTO, ProblemRequestDTO, UUID> {
}
