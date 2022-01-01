package com.berkanaslan.kodsozluk.repository;

import com.berkanaslan.kodsozluk.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends BaseEntityRepository<Topic> {
    Optional<Topic> findByName(String name);
}
