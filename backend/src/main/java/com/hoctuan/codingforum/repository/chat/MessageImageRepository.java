package com.hoctuan.codingforum.repository.chat;

import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.chat.MessageImage;

import java.util.UUID;

@Repository
public interface MessageImageRepository extends BaseRepository<MessageImage, UUID> {
}
