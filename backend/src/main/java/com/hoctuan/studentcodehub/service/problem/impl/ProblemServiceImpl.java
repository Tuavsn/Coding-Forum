package com.hoctuan.studentcodehub.service.problem.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.studentcodehub.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.studentcodehub.model.entity.problem.Problem;
import com.hoctuan.studentcodehub.model.mapper.ProblemMapper;
import com.hoctuan.studentcodehub.repository.problem.ProblemRepository;
import com.hoctuan.studentcodehub.service.problem.ProblemService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProblemServiceImpl extends BaseServiceImpl<
        Problem,
        ProblemResponseDTO,
        ProblemRequestDTO,
        UUID> implements ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemMapper problemMapper;

    public ProblemServiceImpl(ProblemRepository problemRepository, ProblemMapper problemMapper) {
        super(problemRepository, problemMapper);
        this.problemRepository = problemRepository;
        this.problemMapper = problemMapper;
    }
}
