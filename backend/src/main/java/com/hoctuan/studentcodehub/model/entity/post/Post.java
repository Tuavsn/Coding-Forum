package com.hoctuan.studentcodehub.model.entity.post;

import com.hoctuan.studentcodehub.common.BaseEntity;
import com.hoctuan.studentcodehub.constant.PostStatus;
import com.hoctuan.studentcodehub.model.entity.account.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Post extends BaseEntity {
    @ManyToOne(optional = false)
    private Topic topic;

    @ManyToOne(optional = false)
    private User user;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String header;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private PostStatus status = PostStatus.ATIVE;

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
