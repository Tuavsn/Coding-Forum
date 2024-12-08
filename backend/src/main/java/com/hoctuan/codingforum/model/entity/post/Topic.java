package com.hoctuan.codingforum.model.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hoctuan.codingforum.common.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Topic extends BaseEntity {
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String name;

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Post> posts = new HashSet<>();
}
