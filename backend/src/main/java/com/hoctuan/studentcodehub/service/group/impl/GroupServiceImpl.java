package com.hoctuan.studentcodehub.service.group.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.group.GroupRequestDTO;
import com.hoctuan.studentcodehub.model.dto.group.GroupResponseDTO;
import com.hoctuan.studentcodehub.model.entity.group.Group;
import com.hoctuan.studentcodehub.model.mapper.GroupMapper;
import com.hoctuan.studentcodehub.repository.group.GroupRepository;
import com.hoctuan.studentcodehub.service.group.GroupService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupServiceImpl extends BaseServiceImpl<
        Group,
        GroupResponseDTO,
        GroupRequestDTO,
        UUID> implements GroupService {
    private GroupRepository groupRepository;
    private GroupMapper groupMapper;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        super(groupRepository, groupMapper);
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }
}
