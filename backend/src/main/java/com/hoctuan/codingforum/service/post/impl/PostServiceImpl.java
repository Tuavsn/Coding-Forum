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

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of PostService that manages post-related operations.
 * Extends the BaseServiceImpl to inherit common CRUD operations.
 */
@Slf4j
@Service
public class PostServiceImpl extends BaseServiceImpl<Post, PostResponseDTO, PostRequestDTO, UUID>
        implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthContext authContext;
    private final PostImageRepository postImageRepository;

    /**
     * Constructs a new PostServiceImpl with necessary dependencies.
     * 
     * @param postRepository Repository for Post entities
     * @param postMapper Mapper for converting between Post entities and DTOs
     * @param authContext Context for accessing authentication information
     * @param postImageRepository Repository for PostImage entities
     */
    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, AuthContext authContext,
            PostImageRepository postImageRepository) {
        super(postRepository, postMapper, Post.class);
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.authContext = authContext;
        this.postImageRepository = postImageRepository;
        log.debug("Initialized PostServiceImpl");
    }

    /**
     * Specifies the join attribute name for filtering Posts by topics.
     * 
     * @return The name of the join attribute for dynamic filtering
     */
    @Override
    protected String getJoinAttributeName() {
        return "topics";
    }

    /**
     * Creates a new Post entity with the current authenticated user as the author.
     * 
     * @param dto Post data transfer object containing post information
     * @return Response DTO of the created post
     * @throws CustomException if the current user is not found
     */
    @Override
    @Transactional
    public PostResponseDTO save(PostRequestDTO dto) {
        log.debug("Creating a new post");
        
        // Get current user ID from authentication context
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> {
                    log.error("Failed to create post: User not found");
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                }));
        log.debug("Current user ID: {}", userId);
        
        // Set the user as the post author
        dto.setUser(UserRequestDTO.builder().id(userId).build());
        
        // Associate post images with the post
        dto.getPostImage().forEach(image -> image.setPost(dto));
        log.debug("Associated {} images with the post", dto.getPostImage().size());
        
        PostResponseDTO result = super.save(dto);
        log.info("Successfully created post with ID: {}", result.getId());
        
        return result;
    }

    /**
     * Updates an existing Post entity after validating the current user is the post author.
     * 
     * @param dto Post data transfer object containing updated post information
     * @return Response DTO of the updated post
     * @throws CustomException if the current user is not authenticated, the post is not found,
     *         or the current user is not the post author
     */
    @Override
    @Transactional
    public PostResponseDTO update(PostRequestDTO dto) {
        UUID postId = dto.getId();
        log.debug("Updating post with ID: {}", postId);
        
        // Get current user ID from authentication context
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> {
                    log.error("Failed to update post: User not authenticated");
                    return new CustomException(ErrorCode.UNAUTHORIZED);
                }));
        log.debug("Current user ID: {}", userId);
        
        // Check if post exists
        Post existedPost = postRepository.findById(postId).orElseThrow(() -> {
            log.error("Failed to update post: Post with ID {} not found", postId);
            return new CustomException(ErrorCode.POST_NOT_FOUND);
        });
        
        // Validate that the current user is the post author
        validateTheAuthor(existedPost, userId);
        log.debug("Validated user {} is the author of post {}", userId, postId);
        
        // Set the user and associate post images
        dto.setUser(UserRequestDTO.builder().id(userId).build());
        dto.getPostImage().forEach(image -> image.setPost(dto));
        log.debug("Associated {} images with the post", dto.getPostImage().size());
        
        // Update the post
        Post updatedPost = postRepository.save(postMapper.updateModel(dto, existedPost));
        log.info("Successfully updated post with ID: {}", postId);
        
        return postMapper.toDTO(updatedPost);
    }

    /**
     * Deletes a Post entity after validating the current user is the post author.
     * 
     * @param id ID of the post to delete
     * @throws CustomException if the current user is not authenticated, the post is not found,
     *         or the current user is not the post author
     */
    @Override
    @Transactional
    public void delete(UUID id) {
        log.debug("Deleting post with ID: {}", id);
        
        // Get current user ID from authentication context
        UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
                .orElseThrow(() -> {
                    log.error("Failed to delete post: User not authenticated");
                    return new CustomException(ErrorCode.UNAUTHORIZED);
                }));
        log.debug("Current user ID: {}", userId);
        
        // Check if post exists
        Post existedPost = postRepository.findById(id).orElseThrow(() -> {
            log.error("Failed to delete post: Post with ID {} not found", id);
            return new CustomException(ErrorCode.POST_NOT_FOUND);
        });
        
        // Validate that the current user is the post author
        validateTheAuthor(existedPost, userId);
        log.debug("Validated user {} is the author of post {}", userId, id);
        
        // Delete the post
        postRepository.deleteById(id);
        log.info("Successfully deleted post with ID: {}", id);
    }

    /**
     * Finds all posts created by a specific user.
     * 
     * @param userId ID of the user whose posts to retrieve
     * @return List of response DTOs for the user's posts
     */
    @Override
    public List<PostResponseDTO> findPostByUser(UUID userId) {
        log.debug("Finding posts for user with ID: {}", userId);
        
        List<Post> posts = postRepository.findByUserId(userId);
        log.debug("Found {} posts for user with ID: {}", posts.size(), userId);
        
        return postMapper.toDTO(posts);
    }

    /**
     * Validates that the specified user is the author of the post.
     * 
     * @param post The post to check
     * @param userId ID of the user to validate as the author
     * @throws CustomException if the user is not the post author
     */
    private void validateTheAuthor(Post post, UUID userId) {
        log.debug("Validating user {} is the author of post {}", userId, post.getId());
        
        if (!post.getUser().getId().equals(userId)) {
            log.error("Authorization failed: User {} is not the author of post {}", userId, post.getId());
            throw new CustomException(ErrorCode.WRONG_AUTHOR);
        }
        
        log.debug("User is confirmed as the post author");
    }
}