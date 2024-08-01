package com.hoctuan.studentcodehub.constant;

public enum AccountGender {
    MALE("Nam"),
    FEMALE("Nữ");

    private final String displayName;

    AccountGender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
