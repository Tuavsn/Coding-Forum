package com.hoctuan.codingforum.model.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hoctuan.codingforum.common.BaseEntity;
import com.hoctuan.codingforum.constant.PostStatus;
import com.hoctuan.codingforum.model.entity.account.User;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Post extends BaseEntity {
    @ManyToMany
    @JoinTable(
        name = "post_topic",
        joinColumns = @JoinColumn(name = "id"),
        inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<Topic> topics;

    @ManyToOne(optional = false)
    private User user;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String header;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private PostStatus status = PostStatus.ACTIVE;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PostImage> postImage;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PostComment> postComment;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PostReaction> postReactions;
}
