package com.hoctuan.studentcodehub.model.entity.account;

import com.hoctuan.studentcodehub.common.BaseEntity;
import com.hoctuan.studentcodehub.constant.AccountRole;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Role extends BaseEntity {
    private AccountRole role;

    @OneToMany(mappedBy = "role")
    private Set<User> user;
}
