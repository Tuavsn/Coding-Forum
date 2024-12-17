package com.hoctuan.codingforum.repository.problem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.problem.Problem;
import com.hoctuan.codingforum.model.entity.problem.ProblemSubmission;

import java.util.UUID;

@Repository
public interface ProblemSubmissionRepository extends BaseRepository<ProblemSubmission, UUID> {
    @Query("select x from ProblemSubmission x where x.isDeleted = false and x.problem = :problem order by x.createdAt desc")
    Page<ProblemSubmission> findByProblem(Pageable pageable, Problem problem);
}
