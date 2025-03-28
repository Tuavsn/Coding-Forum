package com.hoctuan.codingforum.common;

import com.hoctuan.codingforum.constant.ErrorCode;
import com.hoctuan.codingforum.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class BaseServiceImpl<Model extends BaseEntity, ResponseDTO extends BaseResponseDTO, RequestDTO extends BaseRequestDTO, ID extends UUID>
        implements BaseService<ResponseDTO, RequestDTO, ID> {
    protected final BaseRepository<Model, ID> repository;
    protected final BaseMapper<Model, ResponseDTO, RequestDTO> mapper;
    private final Class<Model> modelClass;

    public BaseServiceImpl(BaseRepository<Model, ID> repository, BaseMapper<Model, ResponseDTO, RequestDTO> mapper,
            Class<Model> modelClass) {
        this.repository = repository;
        this.mapper = mapper;
        this.modelClass = modelClass;
    }

    /**
     * Get all element without paging
     * 
     * @return List of ResponseDTO
     */
    @Override
    public List<ResponseDTO> findAll() {
        return mapper.toDTO(repository.findAll());
    };

    /**
     * Get all element with paging
     * 
     * @param pageable
     * @param search
     * @return Page of ResponseDTO
     */
    public Page<ResponseDTO> findAll(Pageable pageable, String search) {
        return repository.findAll(pageable, "%" + search + "%").map(mapper::toDTO);
    };

    /**
     * Save element
     * 
     * @param dto
     * @return ResponseDTO
     */
    @Transactional
    public ResponseDTO save(RequestDTO dto) {
        Model savedModel = repository.save(mapper.toModel(dto));
        return mapper.toDTO(savedModel);
    };

    /**
     * Update element
     * 
     * @param dto
     * @return ResponseDTO
     */
    @Transactional
    public ResponseDTO update(RequestDTO dto) {
        ID id = (ID) dto.getId();
        Model entity = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND,
                        modelClass.getSimpleName() + " with ID: " + id));
        entity = mapper.toModel(dto);
        return mapper.toDTO(repository.save(entity));
    }

    /**
     * Delete element by id
     * 
     * @param id
     */
    @Transactional
    public void delete(ID id) {
        if (!repository.existsById(id)) {
            throw new CustomException(ErrorCode.NOT_FOUND, modelClass.getSimpleName() + " with ID: " + id);
        }
        repository.deleteById(id);
    };

    /**
     * Get element by id
     * 
     * @param id
     * @return ResponseDTO
     */
    public ResponseDTO getById(ID id) {
        Model model = repository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, modelClass.getSimpleName() + " with ID: " + id));
        return mapper.toDTO(model);
    };
}
