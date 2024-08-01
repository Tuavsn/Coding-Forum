package com.hoctuan.studentcodehub.repository.problem;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.problem.ProblemComment;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProblemCommentRepository extends BaseRepository<ProblemComment, UUID> {
}
