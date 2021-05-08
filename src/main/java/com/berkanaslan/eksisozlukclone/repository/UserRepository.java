package com.berkanaslan.eksisozlukclone.repository;

import com.berkanaslan.eksisozlukclone.model.User;

import java.util.Optional;

public interface UserRepository extends BaseEntityRepository<User> {
    Optional<User> getById(long id);

    Optional<User> findByUsername(String username);
}
