package com.berkanaslan.eksisozlukclone;

import com.berkanaslan.eksisozlukclone.model.User;
import com.berkanaslan.eksisozlukclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceSchemaCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements ApplicationListener<DataSourceSchemaCreatedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(DataSourceSchemaCreatedEvent event) {
        createSuperAdminUser();
    }

    private void createSuperAdminUser() {
        if (userRepository.findByUsername(User.SUPER_ADMIN_USERNAME).isPresent()) {
            return;
        }

        User superAdmin = new User();
        superAdmin.setUsername(User.SUPER_ADMIN_USERNAME);
        superAdmin.setEmail("aslnberkan@gmail.com");
        superAdmin.setFirstName("Super");
        superAdmin.setLastName("Admin");
        superAdmin.setRole(User.Role.ADMIN);
        superAdmin.setEnabled(true);
        superAdmin.setBlocked(false);
        superAdmin.setPassword(passwordEncoder.encode("password"));
        userRepository.save(superAdmin);
    }
}
