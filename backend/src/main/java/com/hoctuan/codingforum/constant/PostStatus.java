package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostStatus {
    ACTIVE(0, "actived", "Đang hoạt động"),
    CLOSED(1, "closed", "Đã đóng");

    private final int code;
    private final String name;
    private final String displayName;
}
