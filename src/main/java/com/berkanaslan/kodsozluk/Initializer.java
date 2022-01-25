package com.berkanaslan.kodsozluk;

import com.berkanaslan.kodsozluk.model.ConnectedApplications;
import com.berkanaslan.kodsozluk.model.Entry;
import com.berkanaslan.kodsozluk.model.Topic;
import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.repository.TopicRepository;
import com.berkanaslan.kodsozluk.repository.UserRepository;
import org.springframework.boot.autoconfigure.jdbc.DataSourceSchemaCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Initializer implements ApplicationListener<DataSourceSchemaCreatedEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TopicRepository topicRepository;

    public Initializer(UserRepository userRepository, PasswordEncoder passwordEncoder, TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.topicRepository = topicRepository;
    }

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
        superAdmin.setRole(User.Role.ADMIN);
        superAdmin.setEnabled(true);
        superAdmin.setBlocked(false);
        superAdmin.setGender(User.Gender.UNDEFINED);
        superAdmin.setPassword(passwordEncoder.encode("password"));
        superAdmin.setEntryCount(1L);

        final ConnectedApplications connectedApplications = new ConnectedApplications();
        connectedApplications.setFacebook("aslberkan");
        connectedApplications.setTwitter("asl_berkan");
        connectedApplications.setGithub("berkanaslan");
        connectedApplications.setInstagram("aslnberkan");

        superAdmin.setConnectedApplications(connectedApplications);
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
        entry.setAuthor(userRepository.findByUsername(User.SUPER_ADMIN_USERNAME).get());
        topic.setEntries(List.of(entry));
        topicRepository.save(topic);
    }
}
