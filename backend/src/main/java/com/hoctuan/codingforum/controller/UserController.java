package com.hoctuan.codingforum.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoctuan.codingforum.common.BaseController;
import com.hoctuan.codingforum.common.BaseResponse;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.dto.user.UserResponseDTO;
import com.hoctuan.codingforum.model.entity.account.User;
// import com.hoctuan.codingforum.service.account.DeviceService;
import com.hoctuan.codingforum.service.account.UserService;
import com.hoctuan.codingforum.service.post.PostService;

import java.util.UUID;

@RestController
@RequestMapping("${spring.api.prefix}/user")
public class UserController extends BaseController<User, UserResponseDTO, UserRequestDTO, UUID> {
    private final UserService userService;
    // private final DeviceService deviceService;
    private final PostService postService;

    // public UserController(UserService userService, DeviceService deviceService, PostService postService) {
    //     super(userService);
    //     this.userService = userService;
    //     this.deviceService = deviceService;
    //     this.postService = postService;
    // }

    public UserController(UserService userService, PostService postService) {
        super(userService);
        this.userService = userService;
        this.postService = postService;
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @GetMapping("/device")
    // public ResponseEntity<BaseResponse> getAllDevices(
    //     @ParameterObject Pageable pageable
    // ) {
    //     return new ResponseEntity<>(
    //     BaseResponse.builder()
    //         .message("Lấy danh sách thiết bị thành công")
    //         .data(deviceService.getAllByUserId(pageable))
    //         .status(HttpStatus.OK.value())
    //         .build(),
    //     HttpStatus.OK);
    // }

    @GetMapping("/{userId}/posts")
    public ResponseEntity<BaseResponse> getAllPostsByUser(
        @PathVariable UUID userId
    ) {
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Lấy danh sách Post thành công")
            .data(postService.findPostByUser(userId))
            .status(HttpStatus.OK.value())
            .build(),
        HttpStatus.OK);
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<BaseResponse> getUserProfile(
            @PathVariable UUID userId
    ) {
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Lấy profile thành công")
            .data(userService.getById(userId))
            .status(HttpStatus.OK.value())
            .build(),
        HttpStatus.OK);
    }

    @GetMapping("/ranking")
    public ResponseEntity<BaseResponse> getUserRanking() {
        return new ResponseEntity<>(
        BaseResponse.builder()
            .message("Lấy bảng xếp hạng thành công")
            .data(userService.getUserRanking())
            .status(HttpStatus.OK.value())
            .build(),
        HttpStatus.OK);
    }
}
