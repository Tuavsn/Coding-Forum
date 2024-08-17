package com.hoctuan.studentcodehub.service.common;

import com.hoctuan.studentcodehub.model.entity.account.User;

public interface AuthContext {
    public User getUserAuthenticated();

    public void clearUserAuthenticated();
}
