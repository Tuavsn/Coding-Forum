package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReactionType {
    LIKE("like", "Like"),
    DISLIKE("dislike", "Dislike");

    private final String name;
    private final String displayName;
}
