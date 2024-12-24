package com.hoctuan.codingforum.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.common.BaseMapper;
import com.hoctuan.codingforum.model.dto.post.PostImageDTO;
import com.hoctuan.codingforum.model.dto.post.PostRequestDTO;
import com.hoctuan.codingforum.model.dto.post.PostResponseDTO;
import com.hoctuan.codingforum.model.entity.post.Post;
import com.hoctuan.codingforum.model.entity.post.PostImage;

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

    public PostImageDTO toImageDTO(PostImage postImages) {
        return modelMapper.map(postImages, PostImageDTO.class);
    }

    public List<PostImageDTO> toImageDTOs(List<PostImage> postImages) {
        return postImages.stream().map(this::toImageDTO).toList();
    }
}
