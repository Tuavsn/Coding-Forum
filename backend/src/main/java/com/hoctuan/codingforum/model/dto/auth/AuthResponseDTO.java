package com.hoctuan.codingforum.model.dto.auth;

import com.hoctuan.codingforum.common.BaseResponseDTO;
import com.hoctuan.codingforum.constant.AccountAchievement;
import com.hoctuan.codingforum.constant.AccountGender;
import com.hoctuan.codingforum.constant.AccountRole;
import com.hoctuan.codingforum.constant.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AuthResponseDTO extends BaseResponseDTO {
    private String email;

    private String username;

    private AccountGender gender;

    private AccountRole role;

    private String avatar;

    private AccountAchievement achievement;

    private double totalSubmissionPoint;

    private String phone;

    private String address;

    private AccountStatus status;

    private String token;
}
