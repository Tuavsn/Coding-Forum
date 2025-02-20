package com.hoctuan.codingforum.service.post.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.exception.NotFoundException;
import com.hoctuan.codingforum.model.dto.post.PostRequestDTO;
import com.hoctuan.codingforum.model.dto.post.PostResponseDTO;
import com.hoctuan.codingforum.model.dto.post.TopicRequestDTO;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.post.Post;
import com.hoctuan.codingforum.model.entity.post.PostImage;
import com.hoctuan.codingforum.model.mapper.PostMapper;
import com.hoctuan.codingforum.repository.account.UserRepository;
import com.hoctuan.codingforum.repository.post.PostImageRepository;
import com.hoctuan.codingforum.repository.post.PostRepository;
import com.hoctuan.codingforum.service.common.AuthContext;
import com.hoctuan.codingforum.service.post.PostService;

import java.util.List;
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
    @Autowired
    private UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        super(postRepository, postMapper);
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    @Transactional
    public PostResponseDTO save(PostRequestDTO dto) {
        User user = authContext.getUserAuthenticated();

        // check if user is post author
        if(dto.getId() != null) {
            Post existedPost = postRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));
            if(existedPost.getUser().getId() != user.getId()) {
                throw new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value());
            }
            if(dto.getPostImage().isEmpty()) {
                dto.setPostImage(postMapper.toImageDTOs(existedPost.getPostImage()));
            }
            dto.setTopic(TopicRequestDTO.builder().id(existedPost.getTopic().getId()).build());
        }

        UserRequestDTO userDTO = UserRequestDTO.builder().id(user.getId()).build();

        dto.setUser(userDTO);

        return this.forceSave(dto);
    }

    @Override
    @Transactional
    public PostResponseDTO forceSave(PostRequestDTO dto) {
        Post post = postMapper.toModel(dto);

        Post savedPost = postRepository.save(post);

        if(!dto.getPostImage().isEmpty()) {
            // delete old images
            postImageRepository.deleteAllByPost(savedPost);

            // save new images
            for(PostImage image : post.getPostImage()) {
                image.setPost(savedPost);
                postImageRepository.save(image);
            }
        }
        
        return postMapper.toDTO(savedPost);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        User user = authContext.getUserAuthenticated();

        Post existedPost = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));

        // check if user is post author
        if(existedPost.getUser().getId() != user.getId()) {
            throw new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value());
        }

        postRepository.deleteById(id);
    };

    @Override
    public List<PostResponseDTO> findPostByUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Id không tìm thấy"));

        return postMapper.toDTO(postRepository.findByUser(user));
    }

}
