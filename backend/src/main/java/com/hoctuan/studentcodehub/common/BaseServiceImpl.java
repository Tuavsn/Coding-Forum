package com.hoctuan.studentcodehub.common;

import com.hoctuan.studentcodehub.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public abstract class BaseServiceImpl<Model extends BaseEntity,
        ResponseDTO extends BaseResponseDTO,
        RequestDTO extends BaseRequestDTO,
        ID extends UUID> implements BaseService<ResponseDTO, RequestDTO, ID> {
    private BaseRepository<Model, ID> repository;
    private BaseMapper<Model, ResponseDTO, RequestDTO> mapper;

    @Override
    public List<ResponseDTO> findAll() {
        return mapper.toDTO(repository.findAll());
    };

    public Page<ResponseDTO> findAll(Pageable pageable, String search) {
        return repository.findAll(pageable, "%" + search + "%").map(mapper::toDTO);
    };

    @Transactional
    public ResponseDTO save(RequestDTO dto) {
        ID id = (ID)dto.getId();
        if (id != null && !repository.existsById(id)) {
            throw new NotFoundException("ID not found");
        }
        return this.forceSave(dto);
    };

    @Transactional
    public ResponseDTO forceSave(RequestDTO dto) {
        Model model = mapper.toModel(dto);
        return mapper.toDTO(repository.save(model));
    }

    @Transactional
    public void delete(ID id) {
        if(!repository.existsById(id)) {
            throw new NotFoundException("ID not found");
        }
        repository.deleteById(id);
    };

    public ResponseDTO getById(ID id) {
        Model model = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("ID not found"));
        return mapper.toDTO(model);
    };
}
