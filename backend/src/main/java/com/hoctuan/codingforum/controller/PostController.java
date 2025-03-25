package com.hoctuan.codingforum.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoctuan.codingforum.common.BaseController;
import com.hoctuan.codingforum.common.BaseResponse;
import com.hoctuan.codingforum.constant.ReactionType;
import com.hoctuan.codingforum.model.dto.post.*;
import com.hoctuan.codingforum.model.entity.post.Post;
import com.hoctuan.codingforum.service.post.CommentReactionService;
import com.hoctuan.codingforum.service.post.PostCommentService;
import com.hoctuan.codingforum.service.post.PostReactionService;
import com.hoctuan.codingforum.service.post.PostService;

import java.util.UUID;

@RestController
@RequestMapping("${spring.api.prefix}/post")
public class PostController extends BaseController<Post, PostResponseDTO, PostRequestDTO, UUID> {
    private final PostService postService;
    private final PostCommentService postCommentService;
    private final PostReactionService postReactionService;
    private final CommentReactionService commentReactionService;

    public PostController(
        PostService postService,
        PostCommentService postCommentService,
        PostReactionService postReactionService,
        CommentReactionService commentReactionService
    ) {
        super(postService);
        this.postService = postService;
        this.postCommentService = postCommentService;
        this.postReactionService = postReactionService;
        this.commentReactionService = commentReactionService;
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<BaseResponse> createComment(
        @PathVariable UUID postId,
        @Valid @RequestBody PostCommentRequestDTO DTO
    ) {
        DTO.setId(null);
        DTO.setPost(PostRequestDTO.builder().id(postId).build());
        PostCommentResponseDTO data = postCommentService.save(DTO);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Tạo thành công")
            .data(data)
            .status(HttpStatus.CREATED.value())
            .build()
        , HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<BaseResponse> updateComment(
        @PathVariable UUID postId,
        @PathVariable UUID commentId,
        @Valid @RequestBody PostCommentRequestDTO DTO
    ) {
        DTO.setId(commentId);
        DTO.setPost(PostRequestDTO.builder().id(postId).build());
        PostCommentResponseDTO data = postCommentService.save(DTO);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Cập nhật thành công")
            .data(data)
            .status(HttpStatus.OK.value())
            .build()
        , HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<BaseResponse> deleteComment(
        @PathVariable UUID commentId
    ) {
        postCommentService.delete(commentId);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Xoá thành công")
            .data(null)
            .status(HttpStatus.ACCEPTED.value())
            .build()
        , HttpStatus.ACCEPTED);
    }

    @PostMapping("/{postId}/react")
    public ResponseEntity<BaseResponse> reactionPost(
        @PathVariable UUID postId,
        @RequestParam ReactionType reactionType
    ) {
        postReactionService.reactionPost(postId, reactionType);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message(reactionType.getDisplayName() + " thành công")
            .data(null)
            .status(HttpStatus.ACCEPTED.value())
            .build()
        , HttpStatus.ACCEPTED);
    }

    @PostMapping("/comment/{commentId}/react")
    public ResponseEntity<BaseResponse> reactionComment(
        @PathVariable UUID commentId,
        @RequestParam ReactionType reactionType
    ) {
        commentReactionService.reactionComment(commentId, reactionType);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message(reactionType.getDisplayName() + " thành công")
            .data(null)
            .status(HttpStatus.ACCEPTED.value())
            .build()
        , HttpStatus.ACCEPTED);
    }
}
