package com.hoctuan.codingforum.model.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.common.BaseMapper;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionResponseDTO;
import com.hoctuan.codingforum.model.entity.problem.ProblemSubmission;

@Component
public class ProblemSubmissonMapper implements BaseMapper<ProblemSubmission, ProblemSubmissionResponseDTO, ProblemSubmissionRequestDTO>{
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProblemSubmissionResponseDTO toDTO(ProblemSubmission problemSubmission) {
        return modelMapper.map(problemSubmission, ProblemSubmissionResponseDTO.class);
    }

    @Override
    public ProblemSubmission toModel(ProblemSubmissionRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, ProblemSubmission.class);
    }

    @Override
    public List<ProblemSubmissionResponseDTO> toDTO(List<ProblemSubmission> problemSubmissions) {
        return problemSubmissions.stream().map(this::toDTO).toList();
    }

    @Override
    public List<ProblemSubmission> toModel(List<ProblemSubmissionRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }
}
