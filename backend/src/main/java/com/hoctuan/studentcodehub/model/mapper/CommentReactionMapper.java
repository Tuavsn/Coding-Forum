package com.hoctuan.studentcodehub.model.mapper;

import com.hoctuan.studentcodehub.common.BaseMapper;
import com.hoctuan.studentcodehub.model.dto.post.CommentReactionRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.CommentReactionResponseDTO;
import com.hoctuan.studentcodehub.model.entity.post.CommentReaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
