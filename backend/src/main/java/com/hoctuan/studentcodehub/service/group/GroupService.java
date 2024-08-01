package com.hoctuan.studentcodehub.service.group;

import com.hoctuan.studentcodehub.common.BaseService;
import com.hoctuan.studentcodehub.model.dto.group.GroupRequestDTO;
import com.hoctuan.studentcodehub.model.dto.group.GroupResponseDTO;

import java.util.UUID;

public interface GroupService extends BaseService<GroupResponseDTO, GroupRequestDTO, UUID> {
}
