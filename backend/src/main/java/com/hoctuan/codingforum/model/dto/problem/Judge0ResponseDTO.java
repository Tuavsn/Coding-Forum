package com.hoctuan.codingforum.model.dto.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Judge0ResponseDTO {
    private String stdout;

    private String stderr;

    private String compilerOutput;

    private String message;

    private int exit_code;

    private int exit_signal;

    private Object status;

    private String token;

    private float time;

    private float wall_time;

    private float memory;
}
