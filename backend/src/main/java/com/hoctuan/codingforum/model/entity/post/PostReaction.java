package com.hoctuan.codingforum.model.entity.post;

import com.hoctuan.codingforum.common.BaseEntity;
import com.hoctuan.codingforum.constant.ReactionType;
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
public class PostReaction extends BaseEntity {
    @ManyToOne(optional = false)
    private Post post;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private ReactionType reactionType;
}
