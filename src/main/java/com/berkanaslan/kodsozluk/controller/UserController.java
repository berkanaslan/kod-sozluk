package com.berkanaslan.kodsozluk.controller;


import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.service.UserService;
import org.springframework.web.bind.annotation.*;


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
        return userService.save(user);
    }

    @GetMapping(params = {"username"})
    public User getUserByUsername(@RequestParam(name = "username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping(path = "follow", params = {"username"})
    public User followUser(@RequestParam(name = "username") String username) {
        return userService.followUser(username);
    }


    @GetMapping(path = "unfollow", params = {"username"})
    public User unfollowUser(@RequestParam(name = "username") String username) {
        return userService.unfollowUser(username);
    }
}
