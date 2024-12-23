package com.hoctuan.codingforum.service.account;

import java.util.List;
import java.util.UUID;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.dto.user.UserResponseDTO;

public interface UserService extends BaseService<UserResponseDTO, UserRequestDTO, UUID> {
    public List<UserResponseDTO> getUserRanking();
}
