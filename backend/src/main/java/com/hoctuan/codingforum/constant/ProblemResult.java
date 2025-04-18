package com.hoctuan.codingforum.constant;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemResult {
    IN_QUEUE(1, "in_queue", "In Queue"),
    PROCESSING(2, "processing", "Processing"),
    ACCEPTED(3, "accepted", "Accepted"),
    WRONG_ANSWER(4, "wrong_answer", "Wrong Answer"),
    TIME_LIMIT(5, "time_limit_exceeded", "Time Limit Exceeded"),
    COMPILE_ERROR(6, "compilation_error", "Compliation Error");

    private final int code;
    private final String name;
    private final String displayName;

    public static String getDisplayNameByCode(int code) {
        return Arrays.stream(values())
                .filter(result -> result.getCode() == code)
                .map(ProblemResult::getDisplayName)
                .findFirst()
                .orElse("Unknown Code"); // Trả về "Unknown Code" nếu không tìm thấy
    }
}
