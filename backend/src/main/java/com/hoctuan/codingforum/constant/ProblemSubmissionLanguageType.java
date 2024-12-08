package com.hoctuan.codingforum.constant;

public enum ProblemSubmissionLanguageType {
    C("1"),
    CPLUSPLUS("2"),
    PYTHON("3"),
    JAVA("4");

    private final String displayName;

    ProblemSubmissionLanguageType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() { return displayName; }
}
