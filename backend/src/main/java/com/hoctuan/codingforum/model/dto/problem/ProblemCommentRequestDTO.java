package com.hoctuan.codingforum.model.dto.problem;

import com.hoctuan.codingforum.common.BaseRequestDTO;
import com.hoctuan.codingforum.model.entity.account.User;
import com.hoctuan.codingforum.model.entity.problem.Problem;

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
public class ProblemCommentRequestDTO extends BaseRequestDTO {
    private Problem problem;

    private User user;

    private String content;
}
