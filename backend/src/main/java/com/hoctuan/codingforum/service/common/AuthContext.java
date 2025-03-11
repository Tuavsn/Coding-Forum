package com.hoctuan.codingforum.service.common;

import java.util.Optional;

public interface AuthContext {
    public Optional<String> getCurrentUserLogin();

    public void clearUserAuthenticated();
}
