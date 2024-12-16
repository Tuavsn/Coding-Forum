package com.hoctuan.codingforum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemResult {
    IN_QUEUE(1, "In Queue", "Đang trong hàng đợi"),
    PROCESSING(2, "Processing", "Đang xử lý"),
    ACCEPTED(3, "Accepted", "Kết quả hợp lệ"),
    WRONG_ANSWER(4, "Wrong answer", "Sai kết quả"),
    TIME_LIMIT(5, "Time Limit Exceeded", "Vượt quá thời gian cho phép"),
    COMPILE_ERROR(6, "Compilation error", "Biên dịch lỗi");

    private final int code;
    private final String name;
    private final String displayName;
}
