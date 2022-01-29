package com.berkanaslan.kodsozluk.service;

import com.berkanaslan.kodsozluk.model.Principal;
import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PrincipalService {

    private final UserRepository userRepository;

    public Optional<User> getUserFromPrincipal() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Principal) {
            return userRepository.findById(((Principal) principal).getUserId());
        }

        return Optional.empty();
    }
}
