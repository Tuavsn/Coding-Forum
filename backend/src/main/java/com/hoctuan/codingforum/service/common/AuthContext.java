package com.hoctuan.codingforum.service.common;

import java.util.Optional;

import com.hoctuan.codingforum.model.entity.account.User;

public interface AuthContext {
    public Optional<String> getCurrentUserLogin();

    public User getCurrentUserEntityLogin();

    public void clearUserAuthenticated();
}
