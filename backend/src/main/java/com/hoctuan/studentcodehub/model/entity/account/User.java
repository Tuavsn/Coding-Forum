package com.hoctuan.studentcodehub.model.entity.account;

import com.hoctuan.studentcodehub.common.BaseEntity;
import com.hoctuan.studentcodehub.constant.AccountGender;
import com.hoctuan.studentcodehub.constant.AccountStatus;
import com.hoctuan.studentcodehub.constant.AuthProvider;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class User extends BaseEntity implements UserDetails {
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String avatar;

    @Column(nullable = false)
    private AccountGender gender;

    private String phone;

    private String address;

    @Column(nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Role role;

    private AccountStatus status = AccountStatus.INACTIVE;

    private AuthProvider authProvider;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Device> devices = new HashSet<>();

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() { return true; }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() { return status == AccountStatus.ACTIVE; }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    @JsonIgnore
    public boolean isEnabled() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
    }

}
