package com.hoctuan.studentcodehub.constant;

public enum PostReactionType {
    LIKE("Thích"),
    LOVE("Thả tim"),
    SMILE("Thả haha"),
    DISLIKE("Không thích");

    private final String displayName;

    PostReactionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
