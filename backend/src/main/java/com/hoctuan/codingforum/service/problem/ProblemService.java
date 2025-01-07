package com.hoctuan.codingforum.service.problem;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.constant.SubmitType;
import com.hoctuan.codingforum.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.SubmissionResultResponseDTO;

public interface ProblemService extends BaseService<ProblemResponseDTO, ProblemRequestDTO, UUID> {
    public ProblemSubmissionResponseDTO submitSolution(UUID id, ProblemSubmissionRequestDTO solutions, SubmitType type);

    public SubmissionResultResponseDTO runSolution(UUID id, ProblemSubmissionRequestDTO solutions, SubmitType type);

    public ProblemSubmissionResponseDTO getSubmitResult(UUID problemSubmissionId);

    public Page<ProblemSubmissionResponseDTO> getSubmissions(UUID id, Pageable pageable);
}
