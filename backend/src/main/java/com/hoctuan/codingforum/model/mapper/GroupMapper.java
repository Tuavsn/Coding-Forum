package com.hoctuan.codingforum.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.common.BaseMapper;
import com.hoctuan.codingforum.model.dto.group.GroupRequestDTO;
import com.hoctuan.codingforum.model.dto.group.GroupResponseDTO;
import com.hoctuan.codingforum.model.entity.group.Group;

import java.util.List;

@Component
public class GroupMapper implements BaseMapper<Group, GroupResponseDTO, GroupRequestDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GroupResponseDTO toDTO(Group group) {
        return modelMapper.map(group, GroupResponseDTO.class);
    }

    @Override
    public Group toModel(GroupRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Group.class);
    }

    @Override
    public List<GroupResponseDTO> toDTO(List<Group> groups) {
        return groups.stream().map(this::toDTO).toList();
    }

    @Override
    public List<Group> toModel(List<GroupRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }
}
