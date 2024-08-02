package com.hoctuan.studentcodehub.model.entity.chat;

import com.hoctuan.studentcodehub.common.BaseEntity;
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
public class MessageImage extends BaseEntity {
    @ManyToOne(optional = false)
    private Message message;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String imageUrl;
}
