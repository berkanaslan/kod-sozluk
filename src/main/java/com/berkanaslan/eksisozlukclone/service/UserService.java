package com.berkanaslan.eksisozlukclone.service;

import com.berkanaslan.eksisozlukclone.model.User;
import com.berkanaslan.eksisozlukclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserInformation(long id) throws RuntimeException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent())
            return optionalUser.get();
        throw new RuntimeException("User not found!");
    }
}