package com.hoctuan.codingforum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoctuan.codingforum.common.BaseController;
import com.hoctuan.codingforum.common.BaseResponse;
import com.hoctuan.codingforum.model.dto.post.TopicRequestDTO;
import com.hoctuan.codingforum.model.dto.post.TopicResponseDTO;
import com.hoctuan.codingforum.model.entity.post.Topic;
import com.hoctuan.codingforum.service.post.TopicService;

import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("api/topic")
public class TopicController extends BaseController<Topic, TopicResponseDTO, TopicRequestDTO, UUID> {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        super(topicService);
        this.topicService = topicService;
    }

    @PreAuthorize("hasRole('SYS_ADMIN')")
    @PostMapping
    @Override
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody TopicRequestDTO DTO) {
        return super.create(DTO);
    }

    @PreAuthorize("hasRole('SYS_ADMIN')")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<BaseResponse> update(
        @PathVariable UUID id,
        @Valid @RequestBody TopicRequestDTO DTO
    ) {
        return super.update(id, DTO);
    }

    @PreAuthorize("hasRole('SYS_ADMIN')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<BaseResponse> delete(@PathVariable UUID id) {
        return super.delete(id);
    }
}
