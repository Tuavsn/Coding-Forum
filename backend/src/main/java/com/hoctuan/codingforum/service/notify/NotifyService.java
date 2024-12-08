package com.hoctuan.codingforum.service.notify;

import java.util.UUID;

import com.hoctuan.codingforum.common.BaseService;
import com.hoctuan.codingforum.model.dto.notify.NotifyRequestDTO;
import com.hoctuan.codingforum.model.dto.notify.NotifyResponseDTO;

public interface NotifyService extends BaseService<NotifyResponseDTO, NotifyRequestDTO, UUID> {
}
