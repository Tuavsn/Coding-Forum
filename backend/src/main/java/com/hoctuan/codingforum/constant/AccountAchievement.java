package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountAchievement {
    BEGINNER(0, "beginner","Beginner"),
    INTERMEDIATE(1, "intermediate","Intermediate"),
    EXPERT(2, "expert","Expert");

    private final int code;
    private final String name;
    private final String displayName;
}
