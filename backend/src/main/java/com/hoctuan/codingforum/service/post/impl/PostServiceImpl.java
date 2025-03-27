package com.hoctuan.codingforum.service.post.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.constant.ErrorCode;
import com.hoctuan.codingforum.exception.CustomException;
import com.hoctuan.codingforum.model.dto.post.PostRequestDTO;
import com.hoctuan.codingforum.model.dto.post.PostResponseDTO;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.entity.post.Post;
import com.hoctuan.codingforum.model.mapper.PostMapper;
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

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, AuthContext authContext,
            PostImageRepository postImageRepository) {
        super(postRepository, postMapper, Post.class);
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.authContext = authContext;
        this.postImageRepository = postImageRepository;
    }

    @Override
    @Transactional
    public PostResponseDTO save(PostRequestDTO dto) {
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)));
        dto.setUser(UserRequestDTO.builder().id(userId).build());
        dto.getPostImage().forEach(image -> image.setPost(dto));
        return super.save(dto);

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
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED)));
        UUID postId = dto.getId();
        Post existedPost = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND));
        // check if user is post author
        validateTheAuthor(existedPost, userId);
        dto.setUser(UserRequestDTO.builder().id(userId).build());
        dto.getPostImage().forEach(image -> image.setPost(dto));
        return postMapper.toDTO(
                postRepository.save(postMapper.updateModel(dto, existedPost)));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED)));
        Post existedPost = postRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND));
        // check if user is post author
        validateTheAuthor(existedPost, userId);
        postRepository.deleteById(id);
    };

    @Override
    public List<PostResponseDTO> findPostByUser(UUID userId) {
        return postMapper.toDTO(postRepository.findByUserId(userId));
    }

    /**
     * Check if client's request is post author
     * 
     * @param postAuthorId
     * @param userId
     * @return
     */
    private void validateTheAuthor(Post post, UUID userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.WRONG_AUTHOR);
        }
    }
}
