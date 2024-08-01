package com.hoctuan.studentcodehub.repository.group;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.group.Group;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupRepository extends BaseRepository<Group, UUID> {
}
