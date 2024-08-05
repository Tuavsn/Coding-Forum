package com.hoctuan.studentcodehub.repository.account;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.account.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceRepository extends BaseRepository<Device, UUID> {
    public Page<Device> findByUserId(UUID userId, Pageable pageable);
}
