package com.berkanaslan.eksisozlukclone;

import com.berkanaslan.eksisozlukclone.controller.UserController;
import com.berkanaslan.eksisozlukclone.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserController userController;

    public Initializer(UserController userController) {
        this.userController = userController;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        createUsers();
    }


    private void createUsers() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("password");
        user.setEnabled(true);
        user.setBlocked(false);
        user.setFirstName("Berkan");
        user.setLastName("Aslan");
        user.setEmail("berkan@aslan.com");
        user.setRole(User.Role.ADMIN);
        userController.save(user);

        User user2 = new User();
        user2.setUsername("user1");
        user2.setPassword("password");
        user2.setEnabled(true);
        user2.setBlocked(false);
        user2.setFirstName("Büşra");
        user2.setLastName("Boyacı");
        user2.setEmail("busra@gmail.com");
        user2.setRole(User.Role.USER);
        userController.save(user2);

        User user3 = new User();
        user3.setUsername("user2");
        user3.setPassword("password");
        user3.setEnabled(true);
        user3.setBlocked(false);
        user3.setFirstName("Umut");
        user3.setLastName("Ozkurt");
        user3.setEmail("rumut@ozkurt.com");
        user3.setRole(User.Role.USER);
        userController.save(user3);

    }
}
