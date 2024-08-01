package com.hoctuan.studentcodehub.common;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;

    private String createdBy;

    private String updatedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean isDeleted = false;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (!(obj instanceof BaseEntity other)) { return false; }
        return id.equals(other.id)
                && createdBy.equals(other.createdBy)
                && updatedBy.equals(other.updatedBy)
                && createdAt == other.createdAt
                && updatedAt == other.updatedAt
                && isDeleted == other.isDeleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdBy, updatedBy, createdAt, updatedAt, isDeleted);
    }

    @Override
    public String toString() {
        return "BaseEntity [id=" + id
                + ", createdBy=" + createdBy
                + ", updatedBy=" + updatedBy
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt
                + ", isDeleted=" + isDeleted
                + "]";
    }
}
