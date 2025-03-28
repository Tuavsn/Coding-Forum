package com.hoctuan.codingforum.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.common.BaseMapper;
import com.hoctuan.codingforum.model.dto.user.UserRequestDTO;
import com.hoctuan.codingforum.model.dto.user.UserResponseDTO;
import com.hoctuan.codingforum.model.entity.account.User;

import java.util.List;


@Component
public class UserMapper implements BaseMapper<User, UserResponseDTO, UserRequestDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponseDTO toDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public User toModel(UserRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, User.class);
    }

    @Override
    public List<UserResponseDTO> toDTO(List<User> users) {
        return users.stream().map(this::toDTO).toList();
    }

    @Override
    public List<User> toModel(List<UserRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }
}
