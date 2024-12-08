package com.hoctuan.codingforum.constant;

public enum AuthProvider {
    LOCAL("Local"),
    GOOGLE("Google"),
    GITHUB("Github");

    private final String displayName;

    AuthProvider(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
