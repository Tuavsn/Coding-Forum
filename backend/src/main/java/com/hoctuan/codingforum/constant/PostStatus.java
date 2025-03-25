package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostStatus {
    ACTIVE("active", "Active"),
    CLOSED("close", "Close");

    private final String name;
    private final String displayName;
}
