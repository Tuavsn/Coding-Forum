package com.hoctuan.codingforum.repository.post;

import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.post.CommentReaction;

import java.util.UUID;

@Repository
public interface CommentReactionRepository extends BaseRepository<CommentReaction, UUID> {
}
