package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatus {
    ACTIVE(0, "active", "Đã kích hoạt"),
    INACTIVE(1, "Inactive", "Chưa kích hoạt"),
    BLOCK(2, "block", "Bị khoá");

    private final int code;
    private final String name;
    private final String displayName;
}
