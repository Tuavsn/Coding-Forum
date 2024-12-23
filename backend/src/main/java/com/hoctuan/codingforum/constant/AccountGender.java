package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountGender {
    MALE(0, "male", "Nam"),
    FEMALE(1, "female", "Nữ");

    private int code;
    private String name;
    private final String displayName;
}
