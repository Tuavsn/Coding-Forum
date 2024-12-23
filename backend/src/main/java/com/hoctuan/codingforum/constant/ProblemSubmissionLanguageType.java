package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemSubmissionLanguageType {
    C(75, "c", "C"),
    CPP(54, "cpp", "C++"),
    PYTHON(71, "python", "Python"),
    JAVA(62, "java", "Java"),
    JAVASCRIPT(63, "javascript", "Javascript"),
    CSHARP(51, "csharp", "C#");

    private final int code;
    private final String name;
    private final String displayName;
}
