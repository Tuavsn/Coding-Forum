package com.hoctuan.studentcodehub.common;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class BaseController<Model extends BaseEntity,
        ResponseDTO extends BaseResponseDTO,
        RequestDTO extends BaseRequestDTO,
        ID extends UUID> {
    private BaseService<ResponseDTO, RequestDTO, ID> baseService;
}
