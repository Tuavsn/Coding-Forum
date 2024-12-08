package com.hoctuan.codingforum.service.problem.impl;

import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.codingforum.model.entity.problem.Problem;
import com.hoctuan.codingforum.model.mapper.ProblemMapper;
import com.hoctuan.codingforum.repository.problem.ProblemRepository;
import com.hoctuan.codingforum.service.problem.ProblemService;

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
