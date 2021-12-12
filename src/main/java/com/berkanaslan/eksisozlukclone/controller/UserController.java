package com.berkanaslan.eksisozlukclone.controller;


import com.berkanaslan.eksisozlukclone.model.User;
import com.berkanaslan.eksisozlukclone.repository.UserRepository;
import com.berkanaslan.eksisozlukclone.util.ExceptionMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping(path = UserController.PATH)
public class UserController extends BaseEntityController<User> {

    static final String PATH = "user";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public String getRequestPath() {
        return PATH;
    }

    @Override
    public long save(@RequestBody User user) {
        UserRepository repository = (UserRepository) getBaseEntityRepository();

        if (user.getId() == 0 && user.getPassword() == null) {
            throw new RuntimeException(ExceptionMessageUtil.getMessageByLocale("message.password_is_required"));
        }

        Optional<User> optional = repository.getById(user.getId());

        if (optional.isPresent()) {
            User existing = optional.get();

            user.setPassword(existing.getPassword());

            repository.save(user);
            return user.getId();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.save(user);
    }
}
