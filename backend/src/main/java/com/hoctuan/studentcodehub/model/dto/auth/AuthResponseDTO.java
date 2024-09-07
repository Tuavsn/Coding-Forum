package com.hoctuan.studentcodehub.model.dto.auth;

import com.hoctuan.studentcodehub.common.BaseResponseDTO;
import com.hoctuan.studentcodehub.constant.AccountAchievement;
import com.hoctuan.studentcodehub.constant.AccountGender;
import com.hoctuan.studentcodehub.constant.AccountRole;
import com.hoctuan.studentcodehub.constant.AccountStatus;
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

    private String phone;

    private String address;

    private AccountStatus status;

    private String token;
}
