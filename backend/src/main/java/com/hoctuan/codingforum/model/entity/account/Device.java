package com.hoctuan.codingforum.model.entity.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import com.hoctuan.codingforum.common.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Device extends BaseEntity {
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String token;

    @Column(nullable = false)
    private boolean isRevoked = false;

    @Column(columnDefinition = "LONGTEXT")
    private String info;

    @Column(columnDefinition = "LONGTEXT")
    private String ip;

    private LocalDateTime expireAt;

    private LocalDateTime lastLoginTime;

    @ManyToOne(optional = false)
    private User user;
}
