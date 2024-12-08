package com.hoctuan.codingforum.repository.account;

import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.account.UserFollow;

import java.util.UUID;

@Repository
public interface UserFollowRepository extends BaseRepository<UserFollow, UUID> {
}
