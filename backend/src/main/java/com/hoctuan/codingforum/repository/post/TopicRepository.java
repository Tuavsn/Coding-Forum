package com.hoctuan.codingforum.repository.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hoctuan.codingforum.common.BaseRepository;
import com.hoctuan.codingforum.model.entity.post.Topic;

import java.util.List;
import java.util.UUID;

@Repository
public interface TopicRepository extends BaseRepository<Topic, UUID> {
    @Override
    @Query("select x from #{#entityName} x where x.isDeleted = false order by x.createdAt asc")
    List<Topic> findAll();
}
