package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotifyStatus {
    READ(0, "read", "Đã xem"),
    UNREAD(1, "unread", "Chưa xem");

    private final int code;
    private final String name;
    private final String displayName;
}
