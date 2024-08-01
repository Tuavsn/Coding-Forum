package com.hoctuan.studentcodehub.model.entity.account;

import com.hoctuan.studentcodehub.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Device extends BaseEntity {
    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private boolean isRevoked = false;

    private String info;

    private String ip;

    private LocalDateTime expireAt;

    private LocalDateTime lastLoginTime;

    @ManyToOne(optional = false)
    private User user;
}
