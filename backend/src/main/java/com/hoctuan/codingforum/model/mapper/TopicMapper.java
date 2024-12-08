package com.hoctuan.codingforum.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoctuan.codingforum.common.BaseMapper;
import com.hoctuan.codingforum.model.dto.post.TopicRequestDTO;
import com.hoctuan.codingforum.model.dto.post.TopicResponseDTO;
import com.hoctuan.codingforum.model.entity.post.Topic;

import java.util.List;

@Component
public class TopicMapper implements BaseMapper<Topic, TopicResponseDTO, TopicRequestDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TopicResponseDTO toDTO(Topic topic) {
        return modelMapper.map(topic, TopicResponseDTO.class);
    }

    @Override
    public Topic toModel(TopicRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, Topic.class);
    }

    @Override
    public List<TopicResponseDTO> toDTO(List<Topic> topics) {
        return topics.stream().map(this::toDTO).toList();
    }

    @Override
    public List<Topic> toModel(List<TopicRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(this::toModel).toList();
    }
}
