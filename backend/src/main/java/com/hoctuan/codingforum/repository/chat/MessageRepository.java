package com.hoctuan.codingforum.repository.chat;

import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.chat.Message;

import java.util.UUID;

@Repository
public interface MessageRepository extends BaseRepository<Message, UUID> {
}
