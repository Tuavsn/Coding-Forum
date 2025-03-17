package com.hoctuan.codingforum.repository.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.post.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends BaseRepository<Post, UUID> {
    @Query("select p from Post p where p.user.id = :userId")
    List<Post> findByUserId(@Param("userId") UUID userId);
}
