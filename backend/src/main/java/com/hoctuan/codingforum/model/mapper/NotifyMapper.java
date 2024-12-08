package com.hoctuan.codingforum.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.common.BaseMapper;
import com.hoctuan.codingforum.model.dto.notify.NotifyRequestDTO;
import com.hoctuan.codingforum.model.dto.notify.NotifyResponseDTO;
import com.hoctuan.codingforum.model.entity.notify.Notify;

import java.util.List;

@Component
public class NotifyMapper implements BaseMapper<Notify, NotifyResponseDTO, NotifyRequestDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public NotifyResponseDTO toDTO(Notify notify) {
        return modelMapper.map(notify, NotifyResponseDTO.class);
    }

    @Override
    public Notify toModel(NotifyRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Notify.class);
    }

    @Override
    public List<NotifyResponseDTO> toDTO(List<Notify> notifies) {
        return notifies.stream().map(this::toDTO).toList();
    }

    @Override
    public List<Notify> toModel(List<NotifyRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }
}
