package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageStatus {
    RECEIVED(0, "received", "Đã nhận"),
    READ(1, "read", "Đã xem"),
    HIDE(2, "hide", "Ẩn");

    private final int code;
    private final String name;
    private final String displayName;
}
