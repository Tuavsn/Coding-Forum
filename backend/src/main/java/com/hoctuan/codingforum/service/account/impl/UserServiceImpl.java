package com.hoctuan.codingforum.service.account.impl;

import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.dto.user.UserResponseDTO;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.mapper.UserMapper;
import com.hoctuan.codingforum.repository.account.UserRepository;
import com.hoctuan.codingforum.service.account.UserService;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends BaseServiceImpl<
        User,
        UserResponseDTO,
        UserRequestDTO,
        UUID> implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponseDTO> getUserRanking() {
        return userMapper.toDTO(userRepository.findTop100UsersOrderedBySubmissionPoint());
    }
}
