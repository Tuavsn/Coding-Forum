package com.hoctuan.codingforum.model.entity.problem;

import com.hoctuan.codingforum.common.BaseEntity;

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
public class SubmissionResult extends BaseEntity {
    @ManyToOne(optional = false)
    private ProblemSubmission submission;

    @Column(nullable = false)
    private int testCaseNum;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String submitResult;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String submitToken;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String submitError;
}
