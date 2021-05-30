package com.berkanaslan.eksisozlukclone.repository;

import com.berkanaslan.eksisozlukclone.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseEntityRepository<User> {
    Optional<User> getById(long id);

    Optional<User> findByUsername(String username);

    List<User> findAllByUsernameContains(String query);
}
