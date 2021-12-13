package com.berkanaslan.eksisozlukclone.repository;

import com.berkanaslan.eksisozlukclone.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseEntityRepository<User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findTop10ByUsernameContains(String query);
}
