package com.hoctuan.codingforum.model.entity.group;

import com.hoctuan.codingforum.common.BaseEntity;
import com.hoctuan.codingforum.constant.AccountRole;
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
public class GroupMember extends BaseEntity {
    @ManyToOne(optional = false)
    private Group group;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private AccountRole role;
}
