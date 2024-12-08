package com.hoctuan.codingforum.model.entity.chat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hoctuan.codingforum.common.BaseEntity;
import com.hoctuan.codingforum.constant.MessageStatus;
import com.hoctuan.codingforum.model.entity.account.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Message extends BaseEntity {
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User source;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User target;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<MessageImage> images = new HashSet<>();

    @Column(nullable = false)
    private MessageStatus status;
}
