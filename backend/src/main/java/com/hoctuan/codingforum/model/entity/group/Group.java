package com.hoctuan.codingforum.model.entity.group;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
public class Group extends BaseEntity {
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<GroupMember> groupMembers = new HashSet<>();
}
