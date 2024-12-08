package com.hoctuan.codingforum.constant;

public enum AccountAchievement {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    EXPERT("Expert");

    private final String displayName;

    AccountAchievement(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
