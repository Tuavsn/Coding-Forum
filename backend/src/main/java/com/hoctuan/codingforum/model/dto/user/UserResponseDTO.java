package com.hoctuan.codingforum.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

import com.hoctuan.codingforum.common.BaseResponseDTO;
import com.hoctuan.codingforum.constant.*;
import com.hoctuan.codingforum.model.entity.account.UserFollow;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserResponseDTO extends BaseResponseDTO {
    private String email;

    private String username;

    private String avatar;

    private AccountGender gender;

    private String address;

    private String phone;

    private AccountRole role;

    private AccountAchievement achievement;

    private double totalSubmissionPoint;

    private AccountStatus status = AccountStatus.INACTIVE;

    private Set<UserFollow> followers = new HashSet<>();
}
