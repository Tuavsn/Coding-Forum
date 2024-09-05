package com.hoctuan.studentcodehub.constant;

public enum ReactionType {
    LIKE("Thích"),
    DISLIKE("Không thích");

    private final String displayName;

    ReactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
