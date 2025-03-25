package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountAchievement {
    BEGINNER("beginner","Beginner"),
    INTERMEDIATE("intermediate","Intermediate"),
    EXPERT("expert","Expert");

    private final String name;
    private final String displayName;
}
