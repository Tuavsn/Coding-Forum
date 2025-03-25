package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {
    ACCESS_TOKEN("access_token", "Access Token"),
    REFRESH_TOKEN("refresh_token", "Refresh Token");

    private final String name;
    private final String displayName;
}
