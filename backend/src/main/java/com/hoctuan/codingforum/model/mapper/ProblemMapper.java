package com.hoctuan.codingforum.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.common.BaseMapper;
import com.hoctuan.codingforum.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.codingforum.model.entity.problem.Problem;

import java.util.List;

@Component
public class ProblemMapper implements BaseMapper<Problem, ProblemResponseDTO, ProblemRequestDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProblemResponseDTO toDTO(Problem problem) {
        return modelMapper.map(problem, ProblemResponseDTO.class);
    }

    @Override
    public Problem toModel(ProblemRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Problem.class);
    }

    @Override
    public List<ProblemResponseDTO> toDTO(List<Problem> problems) {
        return problems.stream().map(this::toDTO).toList();
    }

    @Override
    public List<Problem> toModel(List<ProblemRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }
}
