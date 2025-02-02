package com.hoctuan.codingforum.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.common.BaseMapper;
import com.hoctuan.codingforum.model.dto.post.PostReactionRequestDTO;
import com.hoctuan.codingforum.model.dto.post.PostReactionResponseDTO;
import com.hoctuan.codingforum.model.entity.post.PostReaction;

import java.util.List;

@Component
public class PostReactionMapper implements BaseMapper<PostReaction, PostReactionResponseDTO, PostReactionRequestDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostReactionResponseDTO toDTO(PostReaction postReaction) {
        return modelMapper.map(postReaction, PostReactionResponseDTO.class);
    }

    @Override
    public PostReaction toModel(PostReactionRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, PostReaction.class);
    }

    @Override
    public List<PostReactionResponseDTO> toDTO(List<PostReaction> postReactions) {
        return postReactions.stream().map(this::toDTO).toList();
    }

    @Override
    public List<PostReaction> toModel(List<PostReactionRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }
}
