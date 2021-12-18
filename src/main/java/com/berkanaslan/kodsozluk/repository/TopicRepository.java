package com.berkanaslan.kodsozluk.repository;

import com.berkanaslan.kodsozluk.model.Topic;

import java.util.Optional;


public interface TopicRepository extends BaseEntityRepository<Topic> {
    Optional<Topic> findByName(String name);
}
