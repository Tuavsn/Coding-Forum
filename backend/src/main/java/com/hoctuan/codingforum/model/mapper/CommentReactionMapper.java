package com.hoctuan.codingforum.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.common.BaseMapper;
import com.hoctuan.codingforum.model.dto.post.CommentReactionRequestDTO;
import com.hoctuan.codingforum.model.dto.post.CommentReactionResponseDTO;
import com.hoctuan.codingforum.model.entity.post.CommentReaction;

import java.util.List;

@Component
public class CommentReactionMapper implements BaseMapper<CommentReaction, CommentReactionResponseDTO, CommentReactionRequestDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentReactionResponseDTO toDTO(CommentReaction commentReaction) {
        return modelMapper.map(commentReaction, CommentReactionResponseDTO.class);
    }

    @Override
    public CommentReaction toModel(CommentReactionRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, CommentReaction.class);
    }

    @Override
    public List<CommentReactionResponseDTO> toDTO(List<CommentReaction> commentReactions) {
        return commentReactions.stream().map(this::toDTO).toList();
    }

    @Override
    public List<CommentReaction> toModel(List<CommentReactionRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }

    // @Override
    // public CommentReaction updateModel(CommentReactionRequestDTO requestDTO, CommentReaction commentReaction) {
    //     return modelMapper.map(requestDTO, commentReaction.getClass());
    // }
}
