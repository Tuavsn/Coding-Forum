package com.hoctuan.codingforum.common;

import com.hoctuan.codingforum.constant.ErrorCode;
import com.hoctuan.codingforum.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Abstract base service implementation providing common CRUD operations and
 * dynamic filtering for entities.
 * 
 * @param <Model> The entity model extending BaseEntity
 * @param <ResponseDTO> The response DTO type extending BaseResponseDTO
 * @param <RequestDTO> The request DTO type extending BaseRequestDTO
 * @param <ID> The entity ID type extending UUID
 */
@Slf4j
public abstract class BaseServiceImpl<Model extends BaseEntity, ResponseDTO extends BaseResponseDTO, RequestDTO extends BaseRequestDTO, ID extends UUID>
        implements BaseService<ResponseDTO, RequestDTO, ID> {
    
    protected final BaseRepository<Model, ID> repository;
    protected final BaseMapper<Model, ResponseDTO, RequestDTO> mapper;
    private final Class<Model> modelClass;

    /**
     * Constructs a new BaseServiceImpl with required dependencies.
     * 
     * @param repository The repository for the entity
     * @param mapper The mapper for converting between entity and DTOs
     * @param modelClass The class of the entity model
     */
    public BaseServiceImpl(BaseRepository<Model, ID> repository, BaseMapper<Model, ResponseDTO, RequestDTO> mapper,
            Class<Model> modelClass) {
        this.repository = repository;
        this.mapper = mapper;
        this.modelClass = modelClass;
        log.debug("Initialized BaseServiceImpl for entity class: {}", modelClass.getSimpleName());
    }

    /**
     * Override this method in services that require join filtering.
     * For services that don't need to filter on a joined attribute, return null.
     * Gets join attribute name for dynamic filtering.
     * 
     * @return The name of the join attribute, or null if not needed
     */
    protected String getJoinAttributeName() {
        return null;
    }

    /**
     * Retrieves all entities without paging.
     * 
     * @return List of ResponseDTO objects representing all entities
     */
    @Override
    public List<ResponseDTO> findAll() {
        log.debug("Finding all {} entities", modelClass.getSimpleName());
        List<Model> entities = repository.findAll();
        log.debug("Found {} {} entities", entities.size(), modelClass.getSimpleName());
        return mapper.toDTO(entities);
    }

    /**
     * Retrieves all entities with paging and search capabilities.
     * 
     * @param pageable Pagination information
     * @param search Search string to filter results
     * @return Page of ResponseDTO objects matching the search criteria
     */
    public Page<ResponseDTO> findAll(Pageable pageable, String search) {
        log.debug("Finding {} entities with search '{}' and pagination: page={}, size={}", 
                modelClass.getSimpleName(), search, pageable.getPageNumber(), pageable.getPageSize());
        
        Page<Model> resultPage = repository.findAll(pageable, "%" + search + "%");
        log.debug("Found {} {} entities (total: {})", 
                resultPage.getNumberOfElements(), modelClass.getSimpleName(), resultPage.getTotalElements());
        
        return resultPage.map(mapper::toDTO);
    }

    /**
     * Searches entities with dynamic filtering and pagination.
     *
     * @param request Filter request containing filter criteria and pagination info
     * @return Page of ResponseDTO objects matching the filter criteria
     */
    public Page<ResponseDTO> search(FilterRequest request) {
        log.debug("Searching {} entities with filter request", modelClass.getSimpleName());
        
        // Build specification for root attributes
        Specification<Model> rootSpec = ObjectSpecification.attributeEqual(request.getRootFilter());
        log.debug("Created root specification for {} attributes", 
                request.getRootFilter() != null ? request.getRootFilter().size() : 0);
        
        String joinAttribute = getJoinAttributeName();
        Specification<Model> spec = rootSpec;
        
        // Add join specification if needed
        if (joinAttribute != null && !joinAttribute.trim().isEmpty() && 
                request.getJoinFilter() != null && !request.getJoinFilter().isEmpty()) {
            log.debug("Adding join specification for attribute '{}' with {} filters", 
                    joinAttribute, request.getJoinFilter().size());
            
            Specification<Model> joinSpec = ObjectSpecification.joinAttributeEqual(joinAttribute, request.getJoinFilter());
            spec = ObjectSpecification.combineAttribute(rootSpec, joinSpec);
        }
        
        // Build pageable attribute
        Pageable pageable = PageRequest.of(request.getPageable().getPage(), request.getPageable().getSize());
        log.debug("Created pageable with page {} and size {}", 
                request.getPageable().getPage(), request.getPageable().getSize());
        
        // Execute search
        Page<Model> resultPage = repository.findAll(spec, pageable);
        log.debug("Search found {} {} entities (total: {})", 
                resultPage.getNumberOfElements(), modelClass.getSimpleName(), resultPage.getTotalElements());
        
        return resultPage.map(mapper::toDTO);
    }

    /**
     * Creates a new entity.
     * 
     * @param dto Request DTO containing entity data
     * @return Response DTO of the created entity
     */
    @Transactional
    public ResponseDTO save(RequestDTO dto) {
        log.debug("Creating new {} entity", modelClass.getSimpleName());
        
        Model model = mapper.toModel(dto);
        Model savedModel = repository.save(model);
        
        log.info("Successfully created {} entity with ID: {}", modelClass.getSimpleName(), savedModel.getId());
        return mapper.toDTO(savedModel);
    }

    /**
     * Updates an existing entity.
     * 
     * @param dto Request DTO containing updated entity data
     * @return Response DTO of the updated entity
     * @throws CustomException if entity with given ID does not exist
     */
    @Transactional
    public ResponseDTO update(RequestDTO dto) {
        ID id = (ID) dto.getId();
        log.debug("Updating {} entity with ID: {}", modelClass.getSimpleName(), id);
        
        // Check if entity exists
        Model entity = repository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = modelClass.getSimpleName() + " with ID: " + id;
                    log.error("Failed to update: {} not found", errorMsg);
                    return new CustomException(ErrorCode.NOT_FOUND, errorMsg);
                });
        
        // Update entity
        entity = mapper.toModel(dto);
        Model updatedModel = repository.save(entity);
        
        log.info("Successfully updated {} entity with ID: {}", modelClass.getSimpleName(), id);
        return mapper.toDTO(updatedModel);
    }

    /**
     * Deletes an entity by ID.
     * 
     * @param id ID of entity to delete
     * @throws CustomException if entity with given ID does not exist
     */
    @Transactional
    public void delete(ID id) {
        log.debug("Deleting {} entity with ID: {}", modelClass.getSimpleName(), id);
        
        if (!repository.existsById(id)) {
            String errorMsg = modelClass.getSimpleName() + " with ID: " + id;
            log.error("Failed to delete: {} not found", errorMsg);
            throw new CustomException(ErrorCode.NOT_FOUND, errorMsg);
        }
        
        repository.deleteById(id);
        log.info("Successfully deleted {} entity with ID: {}", modelClass.getSimpleName(), id);
    }

    /**
     * Retrieves an entity by ID.
     * 
     * @param id ID of entity to retrieve
     * @return Response DTO of the found entity
     * @throws CustomException if entity with given ID does not exist
     */
    public ResponseDTO getById(ID id) {
        log.debug("Getting {} entity with ID: {}", modelClass.getSimpleName(), id);
        
        Model model = repository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = modelClass.getSimpleName() + " with ID: " + id;
                    log.error("Failed to get: {} not found", errorMsg);
                    return new CustomException(ErrorCode.NOT_FOUND, errorMsg);
                });
        
        log.debug("Successfully retrieved {} entity with ID: {}", modelClass.getSimpleName(), id);
        return mapper.toDTO(model);
    }
}