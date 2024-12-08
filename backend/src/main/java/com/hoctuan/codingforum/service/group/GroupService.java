package com.hoctuan.codingforum.service.group;

import java.util.UUID;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.model.dto.group.GroupRequestDTO;
import com.hoctuan.codingforum.model.dto.group.GroupResponseDTO;

public interface GroupService extends BaseService<GroupResponseDTO, GroupRequestDTO, UUID> {
}
