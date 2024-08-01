package com.hoctuan.studentcodehub.repository.post;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.post.PostComment;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostCommentRepository extends BaseRepository<PostComment, UUID> {
}
