package com.hoctuan.codingforum.repository.account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.constant.AuthProvider;
import com.hoctuan.codingforum.model.entity.account.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {
    public Optional<User> findByEmailAndAuthProvider(String email, AuthProvider authProvider);

    @Query(value = "SELECT * FROM user ORDER BY total_submission_point DESC LIMIT 100", nativeQuery = true)
    List<User> findTop100UsersOrderedBySubmissionPoint();
}
