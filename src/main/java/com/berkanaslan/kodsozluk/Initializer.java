package com.berkanaslan.kodsozluk;

import com.berkanaslan.kodsozluk.model.Entry;
import com.berkanaslan.kodsozluk.model.Topic;
import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.repository.TopicRepository;
import com.berkanaslan.kodsozluk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceSchemaCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Initializer implements ApplicationListener<DataSourceSchemaCreatedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(DataSourceSchemaCreatedEvent event) {
        createSuperAdminUser();
        createFirstTopicAndEntry();
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

    private void createFirstTopicAndEntry() {
        if (topicRepository.findByName("pena").isPresent()) {
            return;
        }

        final Topic topic = new Topic();
        topic.setName("pena");

        final Entry entry = new Entry();
        entry.setTopic(topic);
        entry.setMessage("gitar calmak icin kullanilan minik plastik garip nesne.");
        topic.setEntries(List.of(entry));
        topicRepository.save(topic);
    }
}
