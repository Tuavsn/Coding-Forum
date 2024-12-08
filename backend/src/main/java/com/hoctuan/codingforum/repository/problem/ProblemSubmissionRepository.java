package com.hoctuan.codingforum.repository.problem;

import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.problem.ProblemSubmission;

import java.util.UUID;

@Repository
public interface ProblemSubmissionRepository extends BaseRepository<ProblemSubmission, UUID> {
}
