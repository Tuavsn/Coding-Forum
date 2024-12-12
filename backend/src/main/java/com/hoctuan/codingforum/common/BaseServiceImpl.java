package com.hoctuan.codingforum.common;

import com.hoctuan.codingforum.exception.NotFoundException;

import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class BaseServiceImpl<Model extends BaseEntity,
        ResponseDTO extends BaseResponseDTO,
        RequestDTO extends BaseRequestDTO,
        ID extends UUID> implements BaseService<ResponseDTO, RequestDTO, ID> {
    @NonNull protected final BaseRepository<Model, ID> repository;
    @NonNull protected final BaseMapper<Model, ResponseDTO, RequestDTO> mapper;

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
