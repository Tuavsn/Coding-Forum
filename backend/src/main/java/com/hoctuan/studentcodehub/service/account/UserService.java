package com.hoctuan.studentcodehub.service.account;

import com.hoctuan.studentcodehub.common.BaseService;
import com.hoctuan.studentcodehub.model.dto.user.UserRequestDTO;
import com.hoctuan.studentcodehub.model.dto.user.UserResponseDTO;

import java.util.UUID;

public interface UserService extends BaseService<UserResponseDTO, UserRequestDTO, UUID> {

}
