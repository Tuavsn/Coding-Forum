package com.hoctuan.studentcodehub.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BaseService<ResponseDTO extends BaseResponseDTO, RequestDTO extends BaseRequestDTO, ID extends UUID> {
    public List<ResponseDTO> findAll();

    public Page<ResponseDTO> findAll(Pageable pageable, String search);

    public ResponseDTO save(RequestDTO dto);

    public void delete(ID id);

    public ResponseDTO getById(ID id);
}
