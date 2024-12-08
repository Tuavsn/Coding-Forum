package com.hoctuan.codingforum.repository.notify;

import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.notify.Notify;

import java.util.UUID;

@Repository
public interface NotifyRepository extends BaseRepository<Notify, UUID> {
}
