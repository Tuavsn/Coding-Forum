package com.hoctuan.studentcodehub.service.account.impl;

import com.hoctuan.studentcodehub.config.AppConstant;
import com.hoctuan.studentcodehub.exception.CustomException;
import com.hoctuan.studentcodehub.exception.NotFoundException;
import com.hoctuan.studentcodehub.model.entity.account.Device;
import com.hoctuan.studentcodehub.model.entity.account.User;
import com.hoctuan.studentcodehub.repository.account.DeviceRepository;
import com.hoctuan.studentcodehub.repository.account.UserRepository;
import com.hoctuan.studentcodehub.service.account.AuthService;
import com.hoctuan.studentcodehub.service.account.DeviceService;
import com.hoctuan.studentcodehub.service.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua_parser.Client;
import ua_parser.Parser;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private AppConstant appConstant;

    @Override
    @Transactional
    public void add(String userId, String token, HttpServletRequest request, LocalDateTime expireAt, LocalDateTime lastLoginTime) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));

        String info = getDeviceDetail(request.getHeader("user-agent"));

        String ip = extractIp(request);

        // Check if device already existed
        Device existingDevice = findExistingDevice(user.getDevices(), info, ip);

        if(existingDevice == null) {
            existingDevice = Device.builder()
                    .id(UUID.randomUUID())
                    .token(token)
                    .info(info)
                    .ip(BCrypt.hashpw(ip, BCrypt.gensalt(appConstant.getLogRounds())))
                    .expireAt(expireAt)
                    .lastLoginTime(lastLoginTime)
                    .build();
            user.getDevices().add(existingDevice);
        } else {
            existingDevice.setToken(token);
            existingDevice.setExpireAt(expireAt);
            existingDevice.setLastLoginTime(lastLoginTime);
        }
    }

    @Override
    @Transactional
    public void revoke(String deviceId, HttpServletRequest request) {
        User user = authService.getUserAuthenticated();

        Device device = user.getDevices().stream().filter(
                d -> d.getId().equals(UUID.fromString(deviceId))
        ).findFirst().orElseThrow(() -> new NotFoundException("Không tìm thấy thiết bị"));

        String info = getDeviceDetail(request.getHeader("user-agent"));

        String ip = extractIp(request);

        if(Objects.equals(device.getInfo(), info) && BCrypt.checkpw(device.getIp(), ip)) {
            throw new CustomException("Không thể xoá thiết bị hiện tại", HttpStatus.BAD_REQUEST.value());
        }

        device.setRevoked(true);

        deviceRepository.save(device);
    }

    private String getDeviceDetail(String userAgent) {
        String deviceDetail = "UNKNOWN";
        Parser parser = new Parser();
        Client client = parser.parse(userAgent);
        if(Objects.nonNull(client)) {
            deviceDetail = client.userAgent.family
                    + " " + client.userAgent.major + "."
                    + client.userAgent.minor + " - "
                    + client.os.family + " " + client.os.major
                    + "." + client.os.minor;
        }
        return deviceDetail;
    }

    private String extractIp(HttpServletRequest request) {
        String clientIp;
        String clientXForwardedForIp = request.getHeader("X-Forwarded-For");

        if(clientXForwardedForIp != null) {
            clientIp = clientXForwardedForIp.split(",")[0];
        } else {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }

    private Device findExistingDevice(Set<Device> devices, String info, String ip) {
        return devices.stream().filter(
                device -> Objects.equals(device.getInfo(), info) && BCrypt.checkpw(ip, device.getIp())
        ).findFirst().orElse(null);
    }
}
