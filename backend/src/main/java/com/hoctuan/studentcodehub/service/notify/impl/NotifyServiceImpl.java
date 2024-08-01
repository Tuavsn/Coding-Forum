package com.hoctuan.studentcodehub.service.notify.impl;

import com.hoctuan.studentcodehub.common.BaseServiceImpl;
import com.hoctuan.studentcodehub.model.dto.notify.NotifyRequestDTO;
import com.hoctuan.studentcodehub.model.dto.notify.NotifyResponseDTO;
import com.hoctuan.studentcodehub.model.entity.notify.Notify;
import com.hoctuan.studentcodehub.service.notify.NotifyService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotifyServiceImpl extends BaseServiceImpl<
        Notify,
        NotifyResponseDTO,
        NotifyRequestDTO,
        UUID> implements NotifyService {
}
