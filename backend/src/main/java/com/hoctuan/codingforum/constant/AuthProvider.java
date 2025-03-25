package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthProvider {
    LOCAL("local", "Local"),
    GOOGLE("google", "Google"),
    LOCAL_AND_GOOGLE("local_and_google", "Local and Google");

    private final String name;
    private final String displayName;
}
