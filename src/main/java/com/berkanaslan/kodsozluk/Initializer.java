package com.berkanaslan.kodsozluk;

import com.berkanaslan.kodsozluk.controller.TopicController;
import com.berkanaslan.kodsozluk.model.Entry;
import com.berkanaslan.kodsozluk.model.Head;
import com.berkanaslan.kodsozluk.model.Topic;
import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.repository.HeadRepository;
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
    private final HeadRepository headRepository;

    public Initializer(UserRepository userRepository, PasswordEncoder passwordEncoder, TopicRepository topicRepository, HeadRepository headRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.topicRepository = topicRepository;
        this.headRepository = headRepository;
    }

    @Override
    public void onApplicationEvent(DataSourceSchemaCreatedEvent event) {
        createSuperAdminUser();
        createFirstTopicAndEntry();
        createHeads();
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

    private void createHeads() {
        if (headRepository.findById(1L).isPresent()) {
            return;
        }

        final String leadingPath = TopicController.PATH;

        headRepository.save(new Head("bugün", leadingPath + "/today"));
        headRepository.save(new Head("gündem", leadingPath + "/trend"));
        headRepository.save(new Head("debe", leadingPath + "/popular"));
        headRepository.save(new Head("sorunsallar", leadingPath + "/issues"));
        headRepository.save(new Head("takip", leadingPath + "/following"));
        headRepository.save(new Head("tarihte bugün", leadingPath + "/today-in-history"));
        headRepository.save(new Head("son", leadingPath + "/latest"));
        headRepository.save(new Head("kenar", leadingPath + "/draft"));
        headRepository.save(new Head("çaylaklar", leadingPath + "/noobs"));
    }
}
