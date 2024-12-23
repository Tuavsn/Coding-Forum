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
    private int language_id;

    private String stdout;

    private int status_id;

    private String stderr;

    private String token;

    private double time;

    private double memory;
}
