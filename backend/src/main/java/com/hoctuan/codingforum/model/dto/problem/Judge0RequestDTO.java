package com.hoctuan.codingforum.model.dto.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Judge0RequestDTO {
    private String sourceCode;

    private String languageId;

    private String compilerOptions;

    private String commandLineArguments;

    private String stdin;
}
