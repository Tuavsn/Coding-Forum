package com.hoctuan.studentcodehub.repository.account;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.account.Role;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends BaseRepository<Role, UUID> {
}
