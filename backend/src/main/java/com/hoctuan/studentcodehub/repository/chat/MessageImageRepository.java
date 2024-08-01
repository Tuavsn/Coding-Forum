package com.hoctuan.studentcodehub.repository.chat;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.chat.MessageImage;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageImageRepository extends BaseRepository<MessageImage, UUID> {
}
