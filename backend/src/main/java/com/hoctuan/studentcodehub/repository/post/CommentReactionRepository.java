package com.hoctuan.studentcodehub.repository.post;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.post.CommentReaction;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentReactionRepository extends BaseRepository<CommentReaction, UUID> {
}
