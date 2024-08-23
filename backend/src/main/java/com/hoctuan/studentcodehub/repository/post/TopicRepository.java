package com.hoctuan.studentcodehub.repository.post;

import com.hoctuan.studentcodehub.common.BaseRepository;
import com.hoctuan.studentcodehub.model.entity.post.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TopicRepository extends BaseRepository<Topic, UUID> {
    @Override
    @Query("select x from #{#entityName} x where x.isDeleted = false order by x.createdAt asc")
    List<Topic> findAll();
}
