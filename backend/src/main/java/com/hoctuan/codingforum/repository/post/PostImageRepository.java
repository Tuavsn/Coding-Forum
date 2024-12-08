package com.hoctuan.codingforum.repository.post;

import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.post.Post;
import com.hoctuan.codingforum.model.entity.post.PostImage;

import java.util.UUID;

@Repository
public interface PostImageRepository extends BaseRepository<PostImage, UUID> {
    void deleteAllByPost(Post post);
}
