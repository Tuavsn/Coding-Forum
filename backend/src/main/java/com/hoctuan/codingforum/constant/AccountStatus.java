package com.hoctuan.codingforum.constant;

public enum AccountStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    BLOCK("Block");

    private final String displayName;

    AccountStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
