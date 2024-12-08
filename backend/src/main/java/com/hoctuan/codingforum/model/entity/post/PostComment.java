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
import com.hoctuan.codingforum.model.entity.account.User;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class PostComment extends BaseEntity {
    @ManyToOne(optional = false)
    private Post post;

    @ManyToOne(optional = false)
    private User user;

    @OneToMany(mappedBy = "postComment", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CommentReaction> commentReactions;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;
}
