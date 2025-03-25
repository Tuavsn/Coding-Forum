package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotifyStatus {
    READ("read", "Read"),
    UNREAD("unread", "Unread");

    private final String name;
    private final String displayName;
}
