package com.hoctuan.studentcodehub.controller;

import com.hoctuan.studentcodehub.common.BaseController;
import com.hoctuan.studentcodehub.model.dto.post.PostRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostResponseDTO;
import com.hoctuan.studentcodehub.model.entity.post.Post;
import com.hoctuan.studentcodehub.service.post.PostService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/post")
public class PostController extends BaseController<
        Post,
        PostResponseDTO,
        PostRequestDTO,
        UUID> {
    private final PostService postService;

    public PostController(PostService postService) {
        super(postService);
        this.postService = postService;
    }
}
