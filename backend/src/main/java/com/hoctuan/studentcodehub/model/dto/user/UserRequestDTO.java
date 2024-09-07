package com.hoctuan.studentcodehub.model.dto.user;

import com.hoctuan.studentcodehub.common.BaseRequestDTO;
import com.hoctuan.studentcodehub.constant.AccountGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserRequestDTO extends BaseRequestDTO {
    @NotBlank(message = "Bạn chưa nhập Username")
    @Size(max = 50, message = "Username quá dài")
    private String username;

    @NotBlank(message = "Bạn chưa nhập mật khẩu")
    private String password;

    private String avatar;

    private AccountGender gender;

    @Size(max = 12, message = "Số điện thoại không hợp lệ")
    private String phone;

    @Size(max = 255, message = "Địa chỉ quá dài")
    private String address;
}
