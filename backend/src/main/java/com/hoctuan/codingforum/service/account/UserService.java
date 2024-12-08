package com.hoctuan.codingforum.service.account;

import java.util.UUID;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.dto.user.UserResponseDTO;

public interface UserService extends BaseService<UserResponseDTO, UserRequestDTO, UUID> {
}
