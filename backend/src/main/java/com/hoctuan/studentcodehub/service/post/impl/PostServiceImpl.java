package com.hoctuan.studentcodehub.service.post.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.post.PostRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostResponseDTO;
import com.hoctuan.studentcodehub.model.entity.post.Post;
import com.hoctuan.studentcodehub.model.mapper.PostMapper;
import com.hoctuan.studentcodehub.repository.post.PostRepository;
import com.hoctuan.studentcodehub.service.post.PostService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostServiceImpl extends BaseServiceImpl<
        Post,
        PostResponseDTO,
        PostRequestDTO,
        UUID> implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        super(postRepository, postMapper);
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }
}
