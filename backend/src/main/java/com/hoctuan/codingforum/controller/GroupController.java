package com.hoctuan.codingforum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoctuan.codingforum.common.BaseController;
import com.hoctuan.codingforum.model.dto.group.GroupRequestDTO;
import com.hoctuan.codingforum.model.dto.group.GroupResponseDTO;
import com.hoctuan.codingforum.model.entity.group.Group;
import com.hoctuan.codingforum.service.group.GroupService;

import java.util.UUID;

@RestController
@RequestMapping("api/group")
public class GroupController extends BaseController<
        Group,
        GroupResponseDTO,
        GroupRequestDTO,
        UUID> {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        super(groupService);
        this.groupService = groupService;
    }
}
