package com.hoctuan.studentcodehub.model.entity.account;

import com.hoctuan.studentcodehub.common.BaseEntity;
import com.hoctuan.studentcodehub.constant.*;
import com.hoctuan.studentcodehub.model.entity.post.Post;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "LONGTEXT")
    private String avatar;

    private AccountGender gender;

    @Column(columnDefinition = "LONGTEXT")
    private String phone;

    @Column(columnDefinition = "LONGTEXT")
    private String address;

    @Column(nullable = false)
    private AccountRole role;

    @Column(nullable = false)
    private AccountAchievement achievement;

    @Column(nullable = false)
    private AccountStatus status = AccountStatus.INACTIVE;

    @Column(nullable = false)
    private AuthProvider authProvider;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Device> devices = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Post> posts = new HashSet<>();
}
