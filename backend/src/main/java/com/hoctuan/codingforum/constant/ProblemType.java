package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemType {
    EASY(0, "easy", "Easy"),
    MEDIUM(1, "medium", "Medium"),
    HARD(2, "hard", "Hard");

    private final int code;
    private final String name;
    private final String displayName;
}
