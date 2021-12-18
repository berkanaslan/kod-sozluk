package com.berkanaslan.kodsozluk.repository;

import com.berkanaslan.kodsozluk.model.User;

import java.util.Optional;

public interface UserRepository extends BaseEntityRepository<User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
