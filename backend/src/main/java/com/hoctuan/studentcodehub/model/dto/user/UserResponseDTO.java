package com.hoctuan.studentcodehub.model.dto.user;

import com.hoctuan.studentcodehub.common.BaseResponseDTO;
import com.hoctuan.studentcodehub.constant.AccountGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO extends BaseResponseDTO {
    private String username;

    private String password;

    private String avatar;

    private AccountGender gender;

    private String phone;

    private String address;
}
