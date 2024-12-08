package com.hoctuan.codingforum.model.entity.problem;

import com.hoctuan.codingforum.common.BaseEntity;
import com.hoctuan.codingforum.constant.ProblemSubmissionLanguageType;
import com.hoctuan.codingforum.model.entity.account.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProblemSubmission extends BaseEntity {
    @ManyToOne(optional = false)
    private Problem problem;

    @ManyToOne(optional = false)
    private User user;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String code;

    @Column(nullable = false)
    private ProblemSubmissionLanguageType languageType;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String result;

    @Column(nullable = false)
    private double score;
}
