package com.hoctuan.codingforum.service.common;

import com.hoctuan.codingforum.model.entity.account.User;

public interface AuthContext {
    public String getCurrentUserId();

    public User getCurrentUserDetails();

    public void clearUserAuthenticated();
}
