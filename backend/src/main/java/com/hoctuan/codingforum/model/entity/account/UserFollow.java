package com.hoctuan.codingforum.model.entity.account;

import com.hoctuan.codingforum.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserFollow extends BaseEntity {
    @ManyToOne(optional = false)
    private User source;

    @ManyToOne(optional = false)
    private User target;
}
