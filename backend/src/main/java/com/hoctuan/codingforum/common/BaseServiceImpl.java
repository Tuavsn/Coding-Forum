package com.hoctuan.codingforum.common;

import com.hoctuan.codingforum.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public abstract class BaseServiceImpl<Model extends BaseEntity,
        ResponseDTO extends BaseResponseDTO,
        RequestDTO extends BaseRequestDTO,
        ID extends UUID> implements BaseService<ResponseDTO, RequestDTO, ID> {
    private final BaseRepository<Model, ID> repository;
    private final BaseMapper<Model, ResponseDTO, RequestDTO> mapper;

    public BaseServiceImpl(
            BaseRepository<Model, ID> repository,
            BaseMapper<Model, ResponseDTO, RequestDTO> mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

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
            throw new NotFoundException("Id không tìm thấy");
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
            throw new NotFoundException("Id không tìm thấy");
        }
        repository.deleteById(id);
    };

    public ResponseDTO getById(ID id) {
        Model model = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id không tìm thấy"));
        return mapper.toDTO(model);
    };
}
