package com.hoctuan.studentcodehub.model.mapper;

import com.hoctuan.studentcodehub.common.BaseMapper;
import com.hoctuan.studentcodehub.model.dto.post.PostRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostResponseDTO;
import com.hoctuan.studentcodehub.model.entity.post.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper implements BaseMapper<Post, PostResponseDTO, PostRequestDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostResponseDTO toDTO(Post post) {
        return modelMapper.map(post, PostResponseDTO.class);
    }

    @Override
    public Post toModel(PostRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Post.class);
    }

    @Override
    public List<PostResponseDTO> toDTO(List<Post> posts) {
        return posts.stream().map(this::toDTO).toList();
    }

    @Override
    public List<Post> toModel(List<PostRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }
}
