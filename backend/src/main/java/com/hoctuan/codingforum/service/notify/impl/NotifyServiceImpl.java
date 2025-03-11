package com.hoctuan.codingforum.service.notify.impl;

import org.springframework.stereotype.Service;

import com.hoctuan.codingforum.common.BaseServiceImpl;
import com.hoctuan.codingforum.model.dto.notify.NotifyRequestDTO;
import com.hoctuan.codingforum.model.dto.notify.NotifyResponseDTO;
import com.hoctuan.codingforum.model.entity.notify.Notify;
import com.hoctuan.codingforum.model.mapper.NotifyMapper;
import com.hoctuan.codingforum.repository.notify.NotifyRepository;
import com.hoctuan.codingforum.service.notify.NotifyService;

import java.util.UUID;

@Service
public class NotifyServiceImpl extends BaseServiceImpl<Notify, NotifyResponseDTO, NotifyRequestDTO, UUID>
        implements NotifyService {
    private final NotifyRepository notifyRepository;
    private final NotifyMapper notifyMapper;

    public NotifyServiceImpl(NotifyRepository notifyRepository, NotifyMapper notifyMapper) {
        super(notifyRepository, notifyMapper);
        this.notifyRepository = notifyRepository;
        this.notifyMapper = notifyMapper;
    }
}
