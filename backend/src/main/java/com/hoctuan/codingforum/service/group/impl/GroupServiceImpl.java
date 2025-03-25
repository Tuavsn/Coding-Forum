package com.hoctuan.codingforum.service.group.impl;

import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.model.dto.group.GroupRequestDTO;
import com.hoctuan.codingforum.model.dto.group.GroupResponseDTO;
import com.hoctuan.codingforum.model.entity.group.Group;
import com.hoctuan.codingforum.model.mapper.GroupMapper;
import com.hoctuan.codingforum.repository.group.GroupRepository;
import com.hoctuan.codingforum.service.group.GroupService;

import java.util.UUID;

@Service
public class GroupServiceImpl extends BaseServiceImpl<Group, GroupResponseDTO, GroupRequestDTO, UUID>
        implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        super(groupRepository, groupMapper, Group.class);
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }
}
