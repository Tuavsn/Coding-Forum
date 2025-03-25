package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountGender {
    MALE("male", "Male"),
    FEMALE("female", "Female");

    private String name;
    private final String displayName;
}
