package com.hoctuan.studentcodehub.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
@Transactional
public interface BaseRepository<Model extends BaseEntity,
        ID extends UUID> extends JpaRepository<Model, ID> {
    @Override
    @Query("select x from #{#entityName} x where x.isDeleted = false order by x.createdAt desc")
    List<Model> findAll();

    @Query("select x from #{#entityName} x order by x.createdAt desc")
    List<Model> findAllWithSoftDeleted();

    @Query("update #{#entityName} x set x.isDeleted = true where x.id = :id")
    @Modifying
    void softDeleteById(ID id);

    @Query("update #{#entityName} x set x.isDeleted = false where x.id = :id")
    @Modifying
    void restoreById(ID id);

    @Query("select x from #{#entityName} x where x.isDeleted = false and cast(BIN_TO_UUID(x.id) as string) like :search order by x.createdAt desc")
    Page<Model> findAll(Pageable pageable, @Param("search") String search);
}
