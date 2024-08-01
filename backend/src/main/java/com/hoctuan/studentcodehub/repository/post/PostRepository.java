package com.hoctuan.studentcodehub.repository.post;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.post.Post;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends BaseRepository<Post, UUID> {
}
