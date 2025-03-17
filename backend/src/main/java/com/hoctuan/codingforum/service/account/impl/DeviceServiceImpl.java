// package com.hoctuan.codingforum.service.account.impl;

// import jakarta.servlet.http.HttpServletRequest;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.hoctuan.codingforum.exception.CustomException;
// import com.hoctuan.codingforum.exception.NotFoundException;
// import com.hoctuan.codingforum.model.dto.device.DeviceResponseDTO;
// import com.hoctuan.codingforum.model.entity.account.Device;
// import com.hoctuan.codingforum.model.entity.account.User;
// import com.hoctuan.codingforum.model.mapper.DeviceMapper;
// import com.hoctuan.codingforum.repository.account.DeviceRepository;
// import com.hoctuan.codingforum.repository.account.UserRepository;
// import com.hoctuan.codingforum.service.account.DeviceService;
// import com.hoctuan.codingforum.service.common.AuthContext;

// import ua_parser.Client;
// import ua_parser.Parser;

// import java.net.InetAddress;
// import java.net.UnknownHostException;
// import java.time.LocalDateTime;
// import java.util.Objects;
// import java.util.Set;
// import java.util.UUID;

// @Service
// public class DeviceServiceImpl implements DeviceService {
//     private final UserRepository userRepository;
//     private final DeviceRepository deviceRepository;
//     private final AuthContext authContext;
//     private final DeviceMapper deviceMapper;

//     public DeviceServiceImpl(UserRepository userRepository, DeviceRepository deviceRepository, AuthContext authContext,
//             DeviceMapper deviceMapper) {
//         this.userRepository = userRepository;
//         this.deviceRepository = deviceRepository;
//         this.authContext = authContext;
//         this.deviceMapper = deviceMapper;
//     }

//     @Override
//     public Page<DeviceResponseDTO> getAllByUserId(Pageable pageable) {
//         UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
//                 .orElseThrow(() -> new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value())));
//         return deviceMapper.toDTO(deviceRepository.findByUserId(userId, pageable));
//     }

//     @Override
//     @Transactional
//     public void add(String userId, String token, HttpServletRequest request, LocalDateTime expireAt, LocalDateTime lastLoginTime) {
//         User user = userRepository.findById(UUID.fromString(userId))
//             .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
//         String info = getDeviceDetail(request.getHeader("user-agent"));
//         String ip = extractIp(request);
//         // Check if device already existed
//         Device existingDevice = findExistingDevice(user.getDevices(), info, ip);
//         if(existingDevice == null) {
//             Device savedDevice = deviceRepository.save(Device.builder()
//                 .user(user)
//                 .token(token)
//                 .info(info)
//                 .ip(ip)
//                 .expireAt(expireAt)
//                 .lastLoginTime(lastLoginTime)
//                 .isDeleted(false)
//                 .createdBy("System")
//                 .updatedBy("System")
//                 .build()
//             );
//             user.getDevices().add(savedDevice);
//         } else {
//             existingDevice.setToken(token);
//             existingDevice.setExpireAt(expireAt);
//             existingDevice.setLastLoginTime(lastLoginTime);
//         }
//         userRepository.save(user);
//     }

//     @Override
//     @Transactional
//     public void revoke(String deviceId, HttpServletRequest request) {
//         UUID userId = UUID.fromString(authContext.getCurrentUserLogin()
//                 .orElseThrow(() -> new CustomException("Yêu cầu không hợp lệ", HttpStatus.BAD_REQUEST.value())));
//         User existedUser = userRepository.findById(userId)
//                 .orElseThrow(() -> new CustomException("Tài khoản không tồn tại", HttpStatus.BAD_REQUEST.value()));
//         Device device = existedUser.getDevices().stream().filter(
//                 d -> d.getId().equals(UUID.fromString(deviceId))).findFirst()
//                 .orElseThrow(() -> new NotFoundException("Không tìm thấy thiết bị"));
//         String info = getDeviceDetail(request.getHeader("user-agent"));
//         String ip = extractIp(request);
//         if (Objects.equals(device.getInfo(), info) && Objects.equals(device.getIp(), ip)) {
//             throw new CustomException("Không thể xoá thiết bị hiện tại", HttpStatus.BAD_REQUEST.value());
//         }
//         device.setRevoked(true);
//         deviceRepository.save(device);
//     }

//     private String getDeviceDetail(String userAgent) {
//         String deviceDetail = "UNKNOWN";
//         Parser parser = new Parser();
//         Client client = parser.parse(userAgent);
//         if (Objects.nonNull(client)) {
//             deviceDetail = client.userAgent.family
//                     + " " + client.userAgent.major + "."
//                     + client.userAgent.minor + " - "
//                     + client.os.family + " " + client.os.major
//                     + "." + client.os.minor;
//         }
//         return deviceDetail;
//     }

//     private String extractIp(HttpServletRequest request) {
//         String clientIp;
//         String clientXForwardedForIp = request.getHeader("X-Forwarded-For");
//         if (clientXForwardedForIp != null) {
//             clientIp = clientXForwardedForIp.split(",")[0];
//         } else {
//             clientIp = request.getRemoteAddr();
//         }
//         try {
//             return convertIpv6ToIpv4(clientIp);
//         } catch (UnknownHostException e) {
//             return clientIp;
//         }
//     }

//     private static String convertIpv6ToIpv4(String ipv6) throws UnknownHostException {
//         // Loopback IPV6 (Localhost)
//         if ("0:0:0:0:0:0:0:1".equals(ipv6) || "::1".equals(ipv6)) {
//             return "127.0.0.1";
//         }
//         InetAddress address = InetAddress.getByName(ipv6);
//         // Kiểm tra nếu địa chỉ IPv6 là địa chỉ IPv4-mapped
//         if (address instanceof java.net.Inet6Address) {
//             byte[] bytes = address.getAddress();
//             if (bytes.length == 16) {
//                 // Kiểm tra prefix 80 bits đầu là zero (IPv4-mapped)
//                 for (int i = 0; i < 10; i++) {
//                     if (bytes[i] != 0) {
//                         return ipv6; // Không phải IPv4-mapped address
//                     }
//                 }
//                 // Kiểm tra 16 bits tiếp theo là 0xFFFF (IPv4-mapped)
//                 if (bytes[10] == (byte) 0xFF && bytes[11] == (byte) 0xFF) {
//                     // Chuyển đổi phần còn lại thành IPv4
//                     int ipv4Part1 = bytes[12] & 0xFF;
//                     int ipv4Part2 = bytes[13] & 0xFF;
//                     int ipv4Part3 = bytes[14] & 0xFF;
//                     int ipv4Part4 = bytes[15] & 0xFF;
//                     return String.format("%d.%d.%d.%d", ipv4Part1, ipv4Part2, ipv4Part3, ipv4Part4);
//                 }
//             }
//         }
//         return ipv6; // Trả về địa chỉ gốc nếu không thể chuyển đổi
//     }

//     private Device findExistingDevice(Set<Device> devices, String info, String ip) {
//         return devices.stream().filter(
//                 device -> Objects.equals(device.getInfo(), info) && Objects.equals(ip, device.getIp())).findFirst()
//                 .orElse(null);
//     }
// }
