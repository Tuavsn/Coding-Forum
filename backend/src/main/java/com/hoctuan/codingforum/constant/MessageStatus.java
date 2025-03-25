package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageStatus {
    RECEIVED("received", "Received"),
    READ("read", "Read"),
    HIDE("hide", "Hide");

    private final String name;
    private final String displayName;
}
