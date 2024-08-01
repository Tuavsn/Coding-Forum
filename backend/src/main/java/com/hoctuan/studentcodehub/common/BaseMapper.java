package com.hoctuan.studentcodehub.common;

import java.util.List;

public interface BaseMapper<Model extends BaseEntity, ResponseDTO extends BaseResponseDTO, RequestDTO extends BaseRequestDTO> {
    ResponseDTO toDTO(Model model);

    Model toModel(RequestDTO requestDTO);

    List<ResponseDTO> toDTO(List<Model> models);

    List<Model> toModels(List<ResponseDTO> responseDTO);
}
