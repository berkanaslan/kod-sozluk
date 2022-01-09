package com.berkanaslan.kodsozluk.service.entry;

import com.berkanaslan.kodsozluk.controller.TopicController;
import com.berkanaslan.kodsozluk.model.Topic;
import com.berkanaslan.kodsozluk.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class EntryAddedEventListener implements ApplicationListener<EntryAddedEvent> {

    private final TopicRepository topicRepository;

    @Override
    public void onApplicationEvent(EntryAddedEvent event) {
        final Topic existTopicOnDB = topicRepository.findById(event.getEntry().getTopic().getId()).orElse(null);

        if (Objects.isNull(existTopicOnDB)) {
            return;
        }

        existTopicOnDB.setDailyTotalEntryCount(existTopicOnDB.getDailyTotalEntryCount() + 1);
        topicRepository.save(existTopicOnDB);
    }
}
