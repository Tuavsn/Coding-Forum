package com.hoctuan.codingforum.model.dto.problem;

import java.util.List;

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
public class Judge0BatchResponseDTO {
    private List<Token> tokens;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Token {
        private String token;
    }
}
