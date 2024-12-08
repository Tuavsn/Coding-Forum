package com.hoctuan.codingforum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoctuan.codingforum.common.BaseController;
import com.hoctuan.codingforum.model.dto.notify.NotifyRequestDTO;
import com.hoctuan.codingforum.model.dto.notify.NotifyResponseDTO;
import com.hoctuan.codingforum.model.entity.notify.Notify;
import com.hoctuan.codingforum.service.notify.NotifyService;

import java.util.UUID;

@RestController
@RequestMapping("api/notify")
public class NotifyController extends BaseController<
        Notify,
        NotifyResponseDTO,
        NotifyRequestDTO,
        UUID> {
    private final NotifyService notifyService;

    public NotifyController(NotifyService notifyService) {
        super(notifyService);
        this.notifyService = notifyService;
    }
}
