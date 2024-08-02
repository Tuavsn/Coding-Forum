package com.hoctuan.studentcodehub.constant;

public enum PostStatus {
    ATIVE("Đang hoạt động"),
    CLOSED("Đã đóng");

    private final String displayName;

    PostStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
