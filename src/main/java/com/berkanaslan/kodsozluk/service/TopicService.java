package com.berkanaslan.kodsozluk.service;

import com.berkanaslan.kodsozluk.model.Topic;
import com.berkanaslan.kodsozluk.repository.TopicRepository;
import com.berkanaslan.kodsozluk.util.I18NUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TopicService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicService.class);

    private final TopicRepository topicRepository;

    /**
     * Every day at 00:00, daily total entry count of topic will reset.
     *
     * @see com.berkanaslan.kodsozluk.model.Topic
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetTotalEntryCountForTodayInTopics() {
        LOGGER.info("Total entry count reset process started.");
        topicRepository.resetDailyTotalEntryCount();
        LOGGER.info("Total entry count reset process completed.");
    }


    public Topic save(final Topic topic) {
        if (topic.getName() == null || topic.getName().isEmpty()) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.topic_name_can_not_be_null"));
        }

        Topic existTopic = topicRepository.findByName(topic.getName()).orElse(null);

        if (existTopic != null) {
            return existTopic;
        }

        return topicRepository.save(topic);
    }

}
