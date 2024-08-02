package com.hoctuan.studentcodehub.constant;

public enum MessageStatus {
    RECEIVED("Đã nhận"),
    READ("Đã xem"),
    HIDE("Ẩn");

    private final String displayName;

    MessageStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
