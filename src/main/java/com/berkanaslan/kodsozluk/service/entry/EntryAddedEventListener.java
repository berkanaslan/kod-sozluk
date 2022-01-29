package com.berkanaslan.kodsozluk.service.entry;

import com.berkanaslan.kodsozluk.model.Entry;
import com.berkanaslan.kodsozluk.model.Topic;
import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.repository.TopicRepository;
import com.berkanaslan.kodsozluk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class EntryAddedEventListener implements ApplicationListener<EntryAddedEvent> {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Async
    @Override
    public void onApplicationEvent(EntryAddedEvent event) {
        increaseEntryCountOfUser(event.getEntry());
        increaseDailyEntryCountOfTopic(event.getEntry());
    }

    private void increaseEntryCountOfUser(Entry entry) {
        if (entry.getAuthor().getId() == 0) {
            return;
        }

        final User author = userRepository.findById(entry.getAuthor().getId()).orElse(null);

        if (Objects.isNull(author)) {
            return;
        }

        author.setEntryCount(author.getEntryCount() + 1);
        userRepository.save(author);
    }

    private void increaseDailyEntryCountOfTopic(Entry entry) {
        final Topic existTopicOnDB = topicRepository.findById(entry.getTopic().getId()).orElse(null);

        if (Objects.isNull(existTopicOnDB)) {
            return;
        }

        existTopicOnDB.setDailyTotalEntryCount(existTopicOnDB.getDailyTotalEntryCount() + 1);
        topicRepository.save(existTopicOnDB);
    }
}
