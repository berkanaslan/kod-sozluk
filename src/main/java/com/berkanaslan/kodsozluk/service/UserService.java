package com.berkanaslan.kodsozluk.service;

import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserInformation(long id) throws RuntimeException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        
        throw new RuntimeException("User not found!");
    }
}