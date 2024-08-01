package com.hoctuan.studentcodehub.repository.group;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.group.GroupMember;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupMemberRepository extends BaseRepository<GroupMember, UUID> {
}
