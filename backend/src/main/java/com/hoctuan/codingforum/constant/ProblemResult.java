package com.hoctuan.codingforum.constant;

public enum ProblemResult {
    ACCEPT("Accept"),
    WRONG_ANSWER("Wrong answer"),
    TIME_LIMIT("Time limit"),
    STACK_OVERFLOW("Stack overflow");

    private final String displayName;

    ProblemResult(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
