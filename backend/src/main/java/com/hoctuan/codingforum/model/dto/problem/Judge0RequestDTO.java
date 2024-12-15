package com.hoctuan.codingforum.model.dto.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Judge0RequestDTO {
    private String source_code;

    private int language_id;

    private String stdin;

    private String expected_output;

    private int cpu_time_limit;

    private int cpu_extra_time;

    private int wall_time_limit;

    private int memory_limit;

    private int stack_limit;
}
