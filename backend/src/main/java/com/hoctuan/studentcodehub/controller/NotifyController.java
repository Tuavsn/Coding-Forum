package com.hoctuan.studentcodehub.controller;

import com.hoctuan.studentcodehub.common.BaseController;
import com.hoctuan.studentcodehub.model.dto.notify.NotifyRequestDTO;
import com.hoctuan.studentcodehub.model.dto.notify.NotifyResponseDTO;
import com.hoctuan.studentcodehub.model.entity.notify.Notify;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/notify")
public class NotifyController extends BaseController<Notify,
        NotifyResponseDTO,
        NotifyRequestDTO,
        UUID> {
}
