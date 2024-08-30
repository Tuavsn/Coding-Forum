package com.hoctuan.studentcodehub.common;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class BaseController<Model extends BaseEntity,
        ResponseDTO extends BaseResponseDTO,
        RequestDTO extends BaseRequestDTO,
        ID extends UUID> {
    private final BaseService<ResponseDTO, RequestDTO, ID> baseService;

    public BaseController(BaseService<ResponseDTO, RequestDTO, ID> baseService) {
        this.baseService = baseService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse> findAll() {
        List<ResponseDTO> data = baseService.findAll();
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Lấy danh sách thành công")
                        .data(data)
                        .status(HttpStatus.OK.value())
                        .build()
                , HttpStatus.OK
        );
    }

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> findAll(
            @ParameterObject Pageable pageable, @RequestParam(defaultValue = "") String search
    ) {
        Page<ResponseDTO> data = baseService.findAll(pageable, search);
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Lấy danh sách thành công")
                        .data(data)
                        .status(HttpStatus.OK.value())
                        .build()
                , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable ID id) {
        ResponseDTO data = baseService.getById(id);
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Lấy thông tin thành công")
                        .data(data)
                        .status(HttpStatus.OK.value())
                        .build()
                , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody RequestDTO DTO) {
        DTO.setId(null);
        ResponseDTO data = baseService.save(DTO);
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Tạo thành công")
                        .data(data)
                        .status(HttpStatus.CREATED.value())
                        .build()
                , HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(
            @PathVariable ID id,
            @Valid @RequestBody RequestDTO DTO
    ) {
        DTO.setId(id);
        ResponseDTO data = baseService.save(DTO);
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Cập nhật thành công")
                        .data(data)
                        .status(HttpStatus.OK.value())
                        .build()
                , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable ID id) {
        baseService.delete(id);
        return new ResponseEntity<>(
                BaseResponse.builder()
                        .message("Xoá thành công")
                        .data(null)
                        .status(HttpStatus.ACCEPTED.value())
                        .build()
                , HttpStatus.ACCEPTED);
    }
}
