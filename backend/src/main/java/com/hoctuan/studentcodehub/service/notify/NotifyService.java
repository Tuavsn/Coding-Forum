package com.hoctuan.studentcodehub.service.notify;

import com.hoctuan.studentcodehub.common.BaseService;
import com.hoctuan.studentcodehub.model.dto.notify.NotifyRequestDTO;
import com.hoctuan.studentcodehub.model.dto.notify.NotifyResponseDTO;

import java.util.UUID;

public interface NotifyService extends BaseService<NotifyResponseDTO, NotifyRequestDTO, UUID> {
}
