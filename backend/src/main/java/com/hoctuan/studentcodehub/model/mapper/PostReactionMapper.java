package com.hoctuan.studentcodehub.model.mapper;

import com.hoctuan.studentcodehub.common.BaseMapper;
import com.hoctuan.studentcodehub.model.dto.post.PostReactionRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostReactionResponseDTO;
import com.hoctuan.studentcodehub.model.entity.post.PostReaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
