package com.hoctuan.codingforum.constant;

public enum ProblemType {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String displayName;

    ProblemType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
