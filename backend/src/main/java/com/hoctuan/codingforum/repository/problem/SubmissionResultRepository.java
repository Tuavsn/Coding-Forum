package com.hoctuan.codingforum.repository.problem;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.problem.SubmissionResult;

@Repository
public interface SubmissionResultRepository extends BaseRepository<SubmissionResult, UUID> {
}