package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemResult {
    ACCEPT(0, "accept", "Kết quả hợp lệ"),
    WRONG_ANSWER(1, "wrong_answer", "Sai kết quả"),
    TIME_LIMIT(2, "time_limit", "Vượt quá thời gian cho phép"),
    STACK_OVERFLOW(3, "stack_overflow", "Vượt quá giới hạn bộ nhớ");

    private final int code;
    private final String name;
    private final String displayName;
}
