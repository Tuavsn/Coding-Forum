package com.hoctuan.studentcodehub.service.post.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.post.PostImageDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostRequestDTO;
import com.hoctuan.studentcodehub.model.dto.post.PostResponseDTO;
import com.hoctuan.studentcodehub.model.dto.user.UserRequestDTO;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.model.entity.post.Post;
import com.hoctuan.studentcodehub.model.entity.post.PostImage;
import com.hoctuan.studentcodehub.model.mapper.PostMapper;
import com.hoctuan.studentcodehub.repository.post.PostImageRepository;
import com.hoctuan.studentcodehub.repository.post.PostRepository;
import com.hoctuan.studentcodehub.service.common.AuthContext;
import com.hoctuan.studentcodehub.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PostServiceImpl extends BaseServiceImpl<
        Post,
        PostResponseDTO,
        PostRequestDTO,
        UUID> implements PostService {
    private PostRepository postRepository;
    private PostMapper postMapper;
    @Autowired
    private AuthContext authContext;
    @Autowired
    private PostImageRepository postImageRepository;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        super(postRepository, postMapper);
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public PostResponseDTO save(PostRequestDTO dto) {
        User user = authContext.getUserAuthenticated();
        UserRequestDTO userDTO = UserRequestDTO.builder().id(user.getId()).build();
        dto.setUser(userDTO);
        return super.save(dto);
    }

    @Override
    @Transactional
    public PostResponseDTO forceSave(PostRequestDTO dto) {
        Post post = postMapper.toModel(dto);
        Post savedPost = postRepository.save(post);
        for(PostImage image : post.getPostImage()) {
            image.setPost(savedPost);
            postImageRepository.save(image);
        }
        return postMapper.toDTO(savedPost);
    }

}
