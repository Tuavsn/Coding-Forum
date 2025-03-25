package com.hoctuan.codingforum.repository.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.post.PostReaction;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostReactionRepository extends BaseRepository<PostReaction, UUID> {
    @Query("select pr from PostReaction pr where pr.user.id = :userId and pr.post.id = :postId")
    Optional<PostReaction> findByUserIdAndPostId(@Param("userId") UUID userId, @Param("postId") UUID postId);
}
