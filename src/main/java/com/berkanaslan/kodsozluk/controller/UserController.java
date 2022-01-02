package com.berkanaslan.kodsozluk.controller;


import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = UserController.PATH)
public class UserController extends BaseEntityController<User, User.Info> {
    static final String PATH = "/user";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Class<User.Info> getEntityInfoClass() {
        return User.Info.class;
    }

    @Override
    public String getRequestPath() {
        return PATH;
    }

    @PostMapping
    @Override
    public User save(@RequestBody final User user) {
        return userService.register(user);
    }
}
