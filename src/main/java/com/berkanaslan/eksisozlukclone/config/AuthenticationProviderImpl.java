package com.berkanaslan.eksisozlukclone.config;

import com.berkanaslan.eksisozlukclone.model.Principal;
import com.berkanaslan.eksisozlukclone.model.User;
import com.berkanaslan.eksisozlukclone.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public Authentication authenticate(Authentication authentication) {
        String presentedUsername = authentication.getName();
        String presentedPassword = authentication.getCredentials().toString();

        Optional<User> userOptional = userRepository.findByUsername(presentedUsername);

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            if (!user.isEnabled()) {
                throw new DisabledException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.disabled",
                        "User account is disabled!"));
            }

            if (user.isBlocked()) {
                throw new LockedException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.locked",
                        "User account is locked"));
            }

            if (!passwordEncoder.matches(presentedPassword, user.getPassword())) {

                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials",
                        "Bad credentials"));
            }

            return new UsernamePasswordAuthenticationToken(
                    Principal.createFrom(user), null, new ArrayList<>()
            );

        } else {
            throw new UsernameNotFoundException("Username Not Found!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
