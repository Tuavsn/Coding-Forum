package com.hoctuan.studentcodehub.model.mapper;

import com.hoctuan.studentcodehub.model.dto.device.DeviceResponseDTO;
import com.hoctuan.studentcodehub.model.entity.account.Device;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    @Autowired
    private ModelMapper modelMapper;

    public DeviceResponseDTO toDTO(Device device) {
        return modelMapper.map(device, DeviceResponseDTO.class);
    }

    public Page<DeviceResponseDTO> toDTO(Page<Device> devices) {
        return devices.map(this::toDTO);
    }
}
