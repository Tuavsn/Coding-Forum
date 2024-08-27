package com.hoctuan.studentcodehub.controller;

import com.hoctuan.studentcodehub.common.BaseController;
import com.hoctuan.studentcodehub.common.BaseResponse;
import com.hoctuan.studentcodehub.model.dto.post.PostCommentRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostCommentResponseDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostResponseDTO;
import com.hoctuan.studentcodehub.model.entity.post.Post;
import com.hoctuan.studentcodehub.service.post.PostCommentService;
import com.hoctuan.studentcodehub.service.post.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/post")
public class PostController extends BaseController<
        Post,
        PostResponseDTO,
        PostRequestDTO,
        UUID> {
    private PostService postService;
    @Autowired
    private PostCommentService postCommentService;

    public PostController(PostService postService) {
        super(postService);
        this.postService = postService;
    }

    @PostMapping("/create-comment")
    public ResponseEntity<BaseResponse> createComment(
            @Valid @RequestBody PostCommentRequestDTO postCommentRequestDTO
    ) {
        postCommentRequestDTO.setId(null);
        PostCommentResponseDTO data = postCommentService.save(postCommentRequestDTO);
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Tạo thành công")
                        .data(data)
                        .status(HttpStatus.CREATED.value())
                        .build()
                , HttpStatus.CREATED);
    }
}
