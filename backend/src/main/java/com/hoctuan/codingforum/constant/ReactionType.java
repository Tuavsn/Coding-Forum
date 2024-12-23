package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReactionType {
    LIKE(0, "like", "Thích"),
    DISLIKE(1, "dislike", "Không thích");

    private final int code;
    private final String name;
    private final String displayName;
}
