package com.berkanaslan.kodsozluk.service;

import com.berkanaslan.kodsozluk.controller.BaseEntityController;
import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.repository.UserRepository;
import com.berkanaslan.kodsozluk.util.I18NUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserInformation(long id) throws RuntimeException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        throw new RuntimeException("User not found!");
    }

    public User register(final User user) {
        if (user.getId() != 0) {
            return this.update(user);
        }

        if (user.getPassword() == null) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.password_is_required"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getUsername() == null) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.username_is_required"));
        }

        if (user.getEmail() == null) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.email_is_required"));
        }

        final Optional<User> isUsernameAlreadyUsed = userRepository.findByUsername(user.getUsername());

        if (isUsernameAlreadyUsed.isPresent()) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.username_already_using"));
        }

        final Optional<User> isEmailAlreadyUsed = userRepository.findByEmail(user.getEmail());

        if (isEmailAlreadyUsed.isPresent()) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.email_already_using"));
        }

        return userRepository.save(user);
    }

    private User update(final User user) {
        if (user.getId() == 0) {
            return this.register(user);
        }

        final Optional<User> userOptional = userRepository.findById(user.getId());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.user_not_found"));
        }

        final User existingUserOnDB = userOptional.get();
        BaseEntityController.copyNonNullProperties(user, existingUserOnDB);

        // Encode the new password if declared.
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUserOnDB);
    }
}