package com.berkanaslan.kodsozluk.controller;

import com.berkanaslan.kodsozluk.model.Topic;
import com.berkanaslan.kodsozluk.repository.TopicRepository;
import com.berkanaslan.kodsozluk.util.I18NUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


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

    @GetMapping(path = "/trend", params = {"pn", "ps", "sb", "sd"})
    public Page<Topic> getAllPaged(
            @RequestParam(name = "pn", defaultValue = "0", required = false) int page,
            @RequestParam(name = "ps", defaultValue = "20", required = false) int size,
            @RequestParam(name = "sb", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sd", defaultValue = SORT_DIRECTION_ASC, required = false) String sortDirection) {
        final Pageable pageable = preparePageRequest(page, size, sortBy, sortDirection);
        final Date today = new Date();
        return ((TopicRepository) getBaseEntityRepository()).findAllPagedByCreationDateOrderByDailyTotalEntryCountDesc(today, pageable);
    }
}
