package com.hoctuan.studentcodehub.model.dto.auth;

import com.hoctuan.studentcodehub.common.BaseRequestDTO;
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
public class AuthRequestDTO extends BaseRequestDTO {
    @NotBlank(message = "Bạn chưa nhập Email")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 50, message = "Email quá dài" )
    private String email;

    @Size(max = 100, message = "Username quá dài")
    private String username;

    @NotBlank(message = "Bạn chưa nhập mật khẩu")
    private String password;
}
