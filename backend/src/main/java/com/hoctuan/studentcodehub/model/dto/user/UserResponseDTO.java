package com.hoctuan.studentcodehub.model.dto.user;

import com.hoctuan.studentcodehub.common.BaseResponseDTO;
import com.hoctuan.studentcodehub.constant.*;
import com.hoctuan.studentcodehub.model.dto.device.DeviceResponseDTO;
import com.hoctuan.studentcodehub.model.entity.account.Device;
import com.hoctuan.studentcodehub.model.entity.account.UserFollow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

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

    private AccountStatus status = AccountStatus.INACTIVE;

    private Set<UserFollow> followers = new HashSet<>();
}
