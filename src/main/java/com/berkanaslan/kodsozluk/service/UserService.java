package com.berkanaslan.kodsozluk.service;

import com.berkanaslan.kodsozluk.controller.BaseEntityController;
import com.berkanaslan.kodsozluk.model.Principal;
import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.repository.UserRepository;
import com.berkanaslan.kodsozluk.util.I18NUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


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

    public User save(final User user) {
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
            return this.save(user);
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

    // TODO: I18N Support:
    public User getUserByUsername(final String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("No such user."));
    }

    // TODO: I18N Support:
    public User followUser(final String username) {
        final User who = getUserBySecurityContextHolder();
        final User whom = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("No such user."));

        final Set<User> followings = who.getFollowing();
        final boolean isAdded = followings.add(whom);

        if (!isAdded) {
            throw new IllegalArgumentException("zaten takiptesin.");
        }

        who.setFollowingCount(who.getFollowingCount() + 1);
        who.setFollowing(followings);

        whom.setFollowersCount(whom.getFollowersCount() + 1);
        userRepository.save(whom);

        return userRepository.save(who);
    }

    // TODO: I18N Support:
    public User unfollowUser(final String username) {
        User who = getUserBySecurityContextHolder();
        User whom = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("No such user."));

        final Set<User> followings = who.getFollowing();
        final boolean isRemoved = followings.remove(whom);

        if (!isRemoved) {
            throw new IllegalArgumentException("zaten takipte değildin.");
        }

        who.setFollowingCount(who.getFollowingCount() - 1);
        who.setFollowing(followings);

        whom.setFollowersCount(whom.getFollowersCount() - 1);
        userRepository.save(whom);

        return userRepository.save(who);
    }

    // TODO: I18N Support:
    private User getUserBySecurityContextHolder() {
        return userRepository.findByUsername(((Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
                .orElseThrow(() -> new IllegalArgumentException("No such user."));
    }
}