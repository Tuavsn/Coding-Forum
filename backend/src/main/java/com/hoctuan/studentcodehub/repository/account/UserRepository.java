package com.hoctuan.studentcodehub.repository.account;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.constant.AuthProvider;
import com.hoctuan.studentcodehub.model.entity.account.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {
    public Optional<User> findByEmailAndAuthProvider(String email, AuthProvider authProvider);
}
