package com.hoctuan.codingforum.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hoctuan.codingforum.common.BaseResponse;
import com.hoctuan.codingforum.model.dto.post.*;
import com.hoctuan.codingforum.service.post.CommentReactionService;
import com.hoctuan.codingforum.service.post.PostCommentService;
import com.hoctuan.codingforum.service.post.PostReactionService;
import com.hoctuan.codingforum.service.post.PostService;

import java.util.UUID;

@RestController
@RequestMapping("api")
public class PostController {
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
        this.postService = postService;
        this.postCommentService = postCommentService;
        this.postReactionService = postReactionService;
        this.commentReactionService = commentReactionService;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<BaseResponse> findById(
        @PathVariable UUID id
    ) {
        PostResponseDTO data = postService.getById(id);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Lấy thông tin thành công")
            .data(data)
            .status(HttpStatus.OK.value())
            .build()
        , HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<BaseResponse> delete(
        @PathVariable UUID postId
    ) {
        postService.delete(postId);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Xoá thành công")
            .data(null)
            .status(HttpStatus.ACCEPTED.value())
            .build()
        , HttpStatus.ACCEPTED);
    }

    @PostMapping("/topic/{topicId}/post")
    public ResponseEntity<BaseResponse> create(
        @PathVariable UUID topicId,
        @Valid @RequestBody PostRequestDTO DTO
    ) {
        DTO.setId(null);
        DTO.setTopic(TopicRequestDTO.builder().id(topicId).build());
        PostResponseDTO data = postService.save(DTO);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Tạo thành công")
            .data(data)
            .status(HttpStatus.CREATED.value())
            .build()
        , HttpStatus.CREATED);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<BaseResponse> update(
        @PathVariable UUID postId,
        @Valid @RequestBody PostRequestDTO DTO
    ) {
        DTO.setId(postId);
        PostResponseDTO data = postService.save(DTO);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Cập nhật thành công")
            .data(data)
            .status(HttpStatus.CREATED.value())
            .build()
        , HttpStatus.CREATED);
    }

    @PostMapping("/post/{postId}/comment")
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

    @PutMapping("/post/{postId}/comment/{commentId}")
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

    @DeleteMapping("/post/comment/{commentId}")
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

    @PostMapping("/post/{postId}/like")
    public ResponseEntity<BaseResponse> likePost(
        @PathVariable UUID postId
    ) {
        postReactionService.likePost(postId);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Like thành công")
            .data(null)
            .status(HttpStatus.ACCEPTED.value())
            .build()
        , HttpStatus.ACCEPTED);
    }

    @PostMapping("/post/{postId}/dislike")
    public ResponseEntity<BaseResponse> unlikePost(
        @PathVariable UUID postId
    ) {
        postReactionService.dislikePost(postId);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Dislike thành công")
            .data(null)
            .status(HttpStatus.ACCEPTED.value())
            .build()
        , HttpStatus.ACCEPTED);
    }

    @PostMapping("/post/comment/{commentId}/like")
    public ResponseEntity<BaseResponse> likeComment(
        @PathVariable UUID commentId
    ) {
        commentReactionService.likeComment(commentId);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Like thành công")
            .data(null)
            .status(HttpStatus.ACCEPTED.value())
            .build()
        , HttpStatus.ACCEPTED);
    }

    @PostMapping("/post/comment/{commentId}/dislike")
    public ResponseEntity<BaseResponse> unlikeComment(
        @PathVariable UUID commentId
    ) {
        commentReactionService.dislikeComment(commentId);
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Dislike thành công")
            .data(null)
            .status(HttpStatus.ACCEPTED.value())
            .build()
        , HttpStatus.ACCEPTED);
    }
}
