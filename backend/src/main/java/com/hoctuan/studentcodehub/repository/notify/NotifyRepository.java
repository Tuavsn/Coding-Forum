package com.hoctuan.studentcodehub.repository.notify;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.notify.Notify;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotifyRepository extends BaseRepository<Notify, UUID> {
}
