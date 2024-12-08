package com.hoctuan.codingforum.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.common.BaseMapper;
import com.hoctuan.codingforum.model.dto.post.PostCommentRequestDTO;
import com.hoctuan.codingforum.model.dto.post.PostCommentResponseDTO;
import com.hoctuan.codingforum.model.entity.post.PostComment;

import java.util.List;

@Component
public class PostCommentMapper implements BaseMapper<PostComment, PostCommentResponseDTO, PostCommentRequestDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostCommentResponseDTO toDTO(PostComment postComment) {
        return modelMapper.map(postComment, PostCommentResponseDTO.class);
    }

    @Override
    public PostComment toModel(PostCommentRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, PostComment.class);
    }

    @Override
    public List<PostCommentResponseDTO> toDTO(List<PostComment> postComments) {
        return postComments.stream().map(this::toDTO).toList();
    }

    @Override
    public List<PostComment> toModel(List<PostCommentRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }    
}
