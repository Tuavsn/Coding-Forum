package com.hoctuan.studentcodehub.model.dto.group;

import com.hoctuan.studentcodehub.common.BaseResponseDTO;
import com.hoctuan.studentcodehub.model.entity.group.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class GroupResponseDTO extends BaseResponseDTO {
    private String title;

    private String description;

    private Set<GroupMember> groupMembers = new HashSet<>();
}
