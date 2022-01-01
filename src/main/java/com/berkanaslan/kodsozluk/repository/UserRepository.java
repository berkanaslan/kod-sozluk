package com.berkanaslan.kodsozluk.repository;

import com.berkanaslan.kodsozluk.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseEntityRepository<User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
