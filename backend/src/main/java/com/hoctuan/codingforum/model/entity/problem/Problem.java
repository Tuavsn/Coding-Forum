package com.hoctuan.codingforum.model.entity.problem;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hoctuan.codingforum.common.BaseEntity;
import com.hoctuan.codingforum.constant.ProblemType;
import com.hoctuan.codingforum.model.entity.account.User;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Problem extends BaseEntity {
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String example;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String tags;

    @Column(nullable = false)
    private ProblemType difficulty;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String testCases;
    
    @ManyToOne(optional = false)
    private User author;
    
    @Column(nullable = false)
    private double totalScore;
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ProblemSubmission> problemSubmissions;
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ProblemComment> comments;
}
