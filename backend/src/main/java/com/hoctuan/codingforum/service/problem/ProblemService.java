package com.hoctuan.codingforum.service.problem;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.model.dto.problem.ProblemRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemResponseDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionRequestDTO;
import com.hoctuan.codingforum.model.dto.problem.ProblemSubmissionResponseDTO;

public interface ProblemService extends BaseService<ProblemResponseDTO, ProblemRequestDTO, UUID> {
    public ProblemSubmissionResponseDTO submitSolution(UUID id, ProblemSubmissionRequestDTO solutions, Map<String, String> params);

    public ProblemSubmissionResponseDTO getSubmitResult(UUID problemSubmissionId, MultiValueMap<String, String> params);

    public Page<ProblemSubmissionResponseDTO> getSubmissions(UUID id, Pageable pageable);

    public void deleteSubmitReuslt(String token);
}
