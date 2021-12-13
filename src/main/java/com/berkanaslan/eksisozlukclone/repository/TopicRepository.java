package com.berkanaslan.eksisozlukclone.repository;

import com.berkanaslan.eksisozlukclone.model.Topic;

import java.util.Optional;


public interface TopicRepository extends BaseEntityRepository<Topic> {
    Optional<Topic> findByName(String name);
}
