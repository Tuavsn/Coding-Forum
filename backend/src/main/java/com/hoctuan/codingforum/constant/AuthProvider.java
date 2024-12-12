package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthProvider {
    LOCAL(0, "local", "Hệ thống"),
    GOOGLE(1, "google", "Google"),
    GITHUB(2, "github", "Github");

    private final int code;
    private final String name;
    private final String displayName;
}
