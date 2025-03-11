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
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.post.Post;
import com.hoctuan.codingforum.model.mapper.PostMapper;
import com.hoctuan.codingforum.repository.account.UserRepository;
import com.hoctuan.codingforum.repository.post.PostImageRepository;
import com.hoctuan.codingforum.repository.post.PostRepository;
import com.hoctuan.codingforum.service.common.AuthContext;
import com.hoctuan.codingforum.service.post.PostService;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl extends BaseServiceImpl<Post, PostResponseDTO, PostRequestDTO, UUID>
        implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthContext authContext;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, AuthContext authContext,
            PostImageRepository postImageRepository, UserRepository userRepository) {
        super(postRepository, postMapper);
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.authContext = authContext;
        this.postImageRepository = postImageRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public PostResponseDTO save(PostRequestDTO dto) {
        UUID userId = UUID.fromString(authContext.getCurrentUserId());
        User existedUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("Tài khoản không tồn tại", HttpStatus.BAD_REQUEST.value()));
        dto.setUser(
                UserRequestDTO
                        .builder()
                        .id(existedUser.getId())
                        .build());
        return postMapper.toDTO(
                postRepository.save(postMapper.toModel(dto)));

        // if (dto.getPostImage().isEmpty()) {
        // dto.setPostImage(postMapper.toImageDTOs(existedPost.getPostImage()));
        // }
        // dto.setTopic(topio);

        // // check if user is post author
        // if(dto.getId() != null) {
        // Post existedPost = postRepository.findById(dto.getId()).orElseThrow(() -> new
        // NotFoundException("Id không tìm thấy"));
        // if(existedPost.getUser().getId() != user.getId()) {
        // throw new CustomException("Yêu cầu không hợp lệ",
        // HttpStatus.BAD_REQUEST.value());
        // }
        // if(dto.getPostImage().isEmpty()) {
        // dto.setPostImage(postMapper.toImageDTOs(existedPost.getPostImage()));
        // }
        // dto.setTopic(TopicRequestDTO.builder().id(existedPost.getTopic().getId()).build());
        // }

        // UserRequestDTO userDTO = UserRequestDTO.builder().id(user.getId()).build();

        // dto.setUser(userDTO);

        // return this.forceSave(dto);
    }

    @Override
    @Transactional
    public PostResponseDTO update(PostRequestDTO dto) {
        UUID userId = UUID.fromString(authContext.getCurrentUserId());
        UUID postId = dto.getId();
        Post existedPost = postRepository.findById(dto.getId()).orElseThrow(
                () -> new NotFoundException("Không tìm thấy bài viết hợp lệ với ID: " + postId));
        // check if user is post author
        if (existedPost.getUser().getId() != userId) {
            throw new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value());
        }
        return postMapper.toDTO(
                postRepository.save(postMapper.updateModel(dto, existedPost)));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = UUID.fromString(authContext.getCurrentUserId());
        Post existedPost = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Không tìm thấy bài viết hợp lệ với ID: " + id));
        // check if user is post author
        if (existedPost.getUser().getId() != userId) {
            throw new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value());
        }
        postRepository.deleteById(id);
    };

    @Override
    public List<PostResponseDTO> findPostByUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Id không tìm thấy"));
        return postMapper.toDTO(postRepository.findByUser(user));
    }
}
