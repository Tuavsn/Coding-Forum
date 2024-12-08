package com.hoctuan.codingforum.service.common;

import com.hoctuan.codingforum.model.entity.account.User;

public interface AuthContext {
    public User getUserAuthenticated();

    public void clearUserAuthenticated();
}
