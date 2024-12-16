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
public class Judge0BatchRequestDTO {
    private List<Judge0RequestDTO> submissions;
}
