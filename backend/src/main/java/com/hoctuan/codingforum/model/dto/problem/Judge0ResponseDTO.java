package com.hoctuan.codingforum.model.dto.problem;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Judge0ResponseDTO {
    private List<Submission> submissions;
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Submission {
        private int language_id;

        private String stdout;

        private int status_id;

        private String stderr;

        private String token;
    }
}
