package com.hoctuan.codingforum.repository.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.account.Device;

import java.util.UUID;

@Repository
public interface DeviceRepository extends BaseRepository<Device, UUID> {
    public Page<Device> findByUserId(UUID userId, Pageable pageable);
}
