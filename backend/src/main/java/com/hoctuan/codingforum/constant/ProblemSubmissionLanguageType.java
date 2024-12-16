package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemSubmissionLanguageType {
    C(75, "c", "C"),
    CPLUSPLUS(54, "cplusplus", "C++"),
    PYTHON(100, "python", "Python"),
    JAVA(91, "java", "Java"),
    JAVASCRIPT(63, "javascript", "Javascript"),
    CSHARP(51, "csharp", "C#");

    private final int code;
    private final String name;
    private final String displayName;
}
