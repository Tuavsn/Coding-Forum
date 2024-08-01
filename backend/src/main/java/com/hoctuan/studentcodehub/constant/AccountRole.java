package com.hoctuan.studentcodehub.constant;

public enum AccountRole {
    SYS_ADMIN("System Admin"),
    SYS_MANAGER("System Manager"),
    GROUP_ADMIN("Group Admin"),
    GROUP_MANAGER("Group Manager"),
    GROUP_MEMBER("Group Member"),
    USER("User");

    private final String displayName;

    AccountRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
