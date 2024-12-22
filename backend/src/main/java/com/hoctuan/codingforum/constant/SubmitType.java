package com.hoctuan.codingforum.constant;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubmitType {
    SYNCHRONOUS("synchronous"),
    ASYNCHRONOUS("asynchronous");

    private final String name;

    public static SubmitType getByName(String name) {
        return Arrays.stream(values())
            .filter(result -> result.name.equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }
}
