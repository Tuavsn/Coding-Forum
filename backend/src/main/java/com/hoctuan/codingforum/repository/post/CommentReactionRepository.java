package com.hoctuan.codingforum.repository.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.post.CommentReaction;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentReactionRepository extends BaseRepository<CommentReaction, UUID> {
    @Query("select cr from CommentReaction cr where cr.user.id = :userId and cr.postComment.id = :commentId")
    Optional<CommentReaction> findByUserIdAndCommentId(@Param("userId") UUID userId, @Param("commentId") UUID commentId);
}
