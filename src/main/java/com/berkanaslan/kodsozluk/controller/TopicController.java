package com.berkanaslan.kodsozluk.controller;

import com.berkanaslan.kodsozluk.model.Topic;
import com.berkanaslan.kodsozluk.repository.TopicRepository;
import com.berkanaslan.kodsozluk.util.I18NUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = TopicController.PATH)
public class TopicController extends BaseEntityController<Topic, Topic.Info> {
    public static final String PATH = "/topic";

    @Override
    public Class<Topic> getEntityClass() {
        return Topic.class;
    }

    @Override
    public Class<Topic.Info> getEntityInfoClass() {
        return Topic.Info.class;
    }

    @Override
    public String getRequestPath() {
        return PATH;
    }

    @Override
    public Topic save(@RequestBody final Topic topic) {
        if (topic.getName() == null || topic.getName().isEmpty()) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.topic_name_can_not_be_null"));
        }

        Topic existTopic = ((TopicRepository) getBaseEntityRepository()).findByName(topic.getName()).orElse(null);

        if (existTopic != null) {
            return existTopic;
        }

        return super.save(topic);
    }
}
