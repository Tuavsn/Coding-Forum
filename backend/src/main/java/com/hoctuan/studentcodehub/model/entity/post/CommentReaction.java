package com.hoctuan.studentcodehub.model.entity.post;

import com.hoctuan.studentcodehub.common.BaseEntity;
import com.hoctuan.studentcodehub.constant.ReactionType;
import com.hoctuan.studentcodehub.model.entity.account.User;
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
public class CommentReaction extends BaseEntity {
    @ManyToOne(optional = false)
    private PostComment postComment;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private ReactionType reactionType;
}
