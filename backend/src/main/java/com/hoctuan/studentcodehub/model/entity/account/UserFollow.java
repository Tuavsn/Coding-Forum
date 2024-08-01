package com.hoctuan.studentcodehub.model.entity.account;

import com.hoctuan.studentcodehub.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserFollow extends BaseEntity {
}
