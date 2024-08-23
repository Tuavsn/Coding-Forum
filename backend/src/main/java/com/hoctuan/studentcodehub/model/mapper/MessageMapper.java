package com.hoctuan.studentcodehub.model.mapper;

import com.hoctuan.studentcodehub.common.BaseMapper;
import com.hoctuan.studentcodehub.model.dto.chat.MessageRequestDTO;
import com.hoctuan.studentcodehub.model.dto.chat.MessageResponseDTO;
import com.hoctuan.studentcodehub.model.entity.chat.Message;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageMapper implements BaseMapper<Message, MessageResponseDTO, MessageRequestDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MessageResponseDTO toDTO(Message message) {
        return modelMapper.map(message, MessageResponseDTO.class);
    }

    @Override
    public Message toModel(MessageRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Message.class);
    }

    @Override
    public List<MessageResponseDTO> toDTO(List<Message> messages) {
        return messages.stream().map(this::toDTO).toList();
    }

    @Override
    public List<Message> toModel(List<MessageRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }
}
