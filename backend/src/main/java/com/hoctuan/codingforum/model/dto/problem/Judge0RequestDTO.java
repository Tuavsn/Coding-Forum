package com.hoctuan.codingforum.model.dto.problem;

import com.hoctuan.codingforum.constant.SubmissionConfigurations;

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

    // private int cpu_time_limit = SubmissionConfigurations.MAX_CPU_TIME_LIMIT;

    // private int cpu_extra_time = SubmissionConfigurations.MAX_CPU_EXTRA_TIME;

    // private int wall_time_limit = SubmissionConfigurations.MAX_WALL_TIME_LIMIT;

    // private int memory_limit = SubmissionConfigurations.MAX_MEMORY_LIMIT;

    // private int stack_limit = SubmissionConfigurations.MAX_STACK_LIMIT;
}
