package com.hoctuan.codingforum.repository.group;

import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.group.GroupMember;

import java.util.UUID;

@Repository
public interface GroupMemberRepository extends BaseRepository<GroupMember, UUID> {
}
