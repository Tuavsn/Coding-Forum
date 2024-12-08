package com.hoctuan.codingforum.constant;

public enum NotifyStatus {
    READ("Đã xem"),
    UNREAD("Chưa xem");

    private final String displayName;

    NotifyStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
