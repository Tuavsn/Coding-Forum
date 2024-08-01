package com.hoctuan.studentcodehub.service.account.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.user.UserRequestDTO;
import com.hoctuan.studentcodehub.model.dto.user.UserResponseDTO;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.service.account.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl extends BaseServiceImpl<
        User,
        UserResponseDTO,
        UserRequestDTO,
        UUID> implements UserService {

}
