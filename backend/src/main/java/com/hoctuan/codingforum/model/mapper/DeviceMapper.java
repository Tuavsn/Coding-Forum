package com.hoctuan.codingforum.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.model.dto.device.DeviceResponseDTO;
import com.hoctuan.codingforum.model.entity.account.Device;

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
