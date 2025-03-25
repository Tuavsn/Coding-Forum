package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemType {
    EASY("easy", "Easy"),
    MEDIUM("medium", "Medium"),
    HARD("hard", "Hard");

    private final String name;
    private final String displayName;
}
