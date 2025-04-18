package com.hoctuan.codingforum.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ObjectSpecification {
    
    /**
     * Creates a specification to filter entities based on direct attribute equality.
     * 
     * @param <T> The type of entity extending BaseEntity
     * @param filterDTOs List of filter criteria containing column names and values to match
     * @return A specification that matches entities with attributes equal to provided values
     */
    public static <T extends BaseEntity> Specification<T> attributeEqual(List<FilterDTO> filterDTOs) {
        return new Specification<T>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (filterDTOs == null || filterDTOs.isEmpty()) {
                    log.debug("No filter criteria provided, returning conjunction (all records)");
                    return builder.conjunction();
                }

                try {
                    List<Predicate> validPredicates = new ArrayList<>();
                    
                    for (FilterDTO filter : filterDTOs) {
                        try {
                            Path<?> path = root.get(filter.getColumnName());
                            validPredicates.add(builder.equal(path, filter.getColumnValue()));
                            log.debug("Added predicate for column '{}' with value '{}'", 
                                    filter.getColumnName(), filter.getColumnValue());
                        } catch (IllegalArgumentException e) {
                            log.warn("Column '{}' not found in entity, skipping this filter", 
                                    filter.getColumnName());
                        }
                    }
                    
                    return validPredicates.isEmpty() ? 
                        builder.conjunction() : 
                        builder.and(validPredicates.toArray(new Predicate[0]));
                    
                } catch (Exception e) {
                    log.error("Error creating filter predicates", e);
                    return builder.conjunction();
                }
            }
        };
    }

    /**
     * Creates a specification to filter entities based on joined entity attribute equality.
     * Handles missing attributes gracefully by skipping invalid filters.
     * 
     * @param <T> The type of entity extending BaseEntity
     * @param <Y> The type of joined entity extending BaseEntity
     * @param joinAttribute The attribute name used for joining
     * @param filterDTOs List of filter criteria for the joined entity
     * @return A specification that matches entities with joined attributes equal to provided values
     */
    public static <T extends BaseEntity, Y extends BaseEntity> Specification<T> joinAttributeEqual(String joinAttribute, List<FilterDTO> filterDTOs) {
        return new Specification<T>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (filterDTOs == null || filterDTOs.isEmpty()) {
                    log.debug("No join filter criteria provided, returning conjunction (all records)");
                    return builder.conjunction();
                }

                try {
                    Join<T, Y> join = root.join(joinAttribute, JoinType.INNER);
                    log.debug("Successfully created join on attribute: {}", joinAttribute);
    
                    query.distinct(true);

                    List<Predicate> validPredicates = new ArrayList<>();

                    for (FilterDTO filter: filterDTOs) {
                        try {
                            Path<?> path = join.get(filter.getColumnName());
                            validPredicates.add(builder.equal(path, filter.getColumnValue()));
                            log.debug("Added join predicate for column '{}' with value '{}'", 
                                    filter.getColumnName(), filter.getColumnValue());
                        } catch (IllegalArgumentException e) {
                            log.warn("Column '{}' not found in joined entity '{}', skipping this filter", 
                                    filter.getColumnName(), joinAttribute);
                        }
                    }

                    if (validPredicates.isEmpty()) {
                        log.warn("No valid predicates could be created for join on '{}'", joinAttribute);
                        return builder.conjunction();
                    }
                    
                    return builder.and(validPredicates.toArray(new Predicate[0]));
                } catch (IllegalArgumentException e) {
                    log.error("Join attribute '{}' not found in entity, returning conjunction", joinAttribute);
                    return builder.conjunction();
                } catch (Exception e) {
                    log.error("Error creating join predicates for attribute '{}'", joinAttribute, e);
                    return builder.conjunction();
                }
            }
        };
    }

    /**
     * Combines two specifications using a logical AND operation.
     * Useful for combining direct attribute filters with joined attribute filters.
     * 
     * @param <T> The type of entity extending BaseEntity
     * @param rootSpec Specification for filtering on root entity attributes
     * @param joinSpec Specification for filtering on joined entity attributes
     * @return A combined specification applying both sets of criteria
     */
    public static <T extends BaseEntity> Specification<T> combineAttribute(Specification<T> rootSpec, Specification<T> joinSpec) {
        log.debug("Combining root and join specifications");
        return Specification.where(rootSpec).and(joinSpec);
    }
}