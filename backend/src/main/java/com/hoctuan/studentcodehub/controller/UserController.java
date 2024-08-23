package com.hoctuan.studentcodehub.controller;

import com.hoctuan.studentcodehub.common.BaseController;
import com.hoctuan.studentcodehub.common.BaseResponse;
import com.hoctuan.studentcodehub.model.dto.user.UserRequestDTO;
import com.hoctuan.studentcodehub.model.dto.user.UserResponseDTO;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.service.account.DeviceService;
import com.hoctuan.studentcodehub.service.account.UserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController extends BaseController<
        User,
        UserResponseDTO,
        UserRequestDTO,
        UUID> {
    private UserService userService;
    @Autowired
    private DeviceService deviceService;

    public UserController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/device")
    public ResponseEntity<BaseResponse> getAllDevices(
            @ParameterObject Pageable pageable
    ) {
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Lấy danh sách thiết bị thành công")
                        .data(deviceService.getAllByUserId(pageable))
                        .status(HttpStatus.OK.value())
                        .build()
                , HttpStatus.OK
        );
    }
}
