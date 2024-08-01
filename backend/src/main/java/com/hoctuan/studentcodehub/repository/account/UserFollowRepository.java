package com.hoctuan.studentcodehub.repository.account;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.account.UserFollow;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserFollowRepository extends BaseRepository<UserFollow, UUID> {
}
