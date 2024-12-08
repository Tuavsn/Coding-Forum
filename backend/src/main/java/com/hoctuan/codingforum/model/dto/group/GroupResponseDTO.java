package com.hoctuan.codingforum.model.dto.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

import com.hoctuan.codingforum.common.BaseResponseDTO;
import com.hoctuan.codingforum.model.entity.group.GroupMember;

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
