package com.hoctuan.codingforum.repository.post;

import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.post.Post;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends BaseRepository<Post, UUID> {
    List<Post> findByUser(User user);
}
