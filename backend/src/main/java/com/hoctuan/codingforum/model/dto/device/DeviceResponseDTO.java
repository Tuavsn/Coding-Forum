package com.hoctuan.codingforum.model.dto.device;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import com.hoctuan.codingforum.common.BaseResponseDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class DeviceResponseDTO extends BaseResponseDTO {
    private String info;

    private String ip;

    private String token;

    private LocalDateTime expireAt;

    private LocalDateTime lastLoginTime;

    private boolean isRevoked;
}
