package com.hoctuan.studentcodehub.repository.problem;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.problem.Problem;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProblemRepository extends BaseRepository<Problem, UUID> {
}
