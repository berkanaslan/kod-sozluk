package com.berkanaslan.kodsozluk.controller;

import com.berkanaslan.kodsozluk.model.Topic;
import com.berkanaslan.kodsozluk.repository.TopicRepository;
import com.berkanaslan.kodsozluk.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping(path = TopicController.PATH)
public class TopicController extends BaseEntityController<Topic, Topic.Info> {
    public static final String PATH = "/topic";

    @Autowired
    private TopicService topicService;

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
        return topicService.save(topic);
    }

    @GetMapping(path = "/trend", params = {"pn", "ps", "sb", "sd"})
    public Page<Topic> getAllPagedTrends(
            @RequestParam(name = "pn", defaultValue = "0", required = false) int page,
            @RequestParam(name = "ps", defaultValue = "20", required = false) int size,
            @RequestParam(name = "sb", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sd", defaultValue = SORT_DIRECTION_ASC, required = false) String sortDirection) {
        final Pageable pageable = preparePageRequest(page, size, sortBy, sortDirection);
        return ((TopicRepository) getBaseEntityRepository()).findAllPagedByCreationDateOrderByDailyTotalEntryCountDesc(new Date(), pageable);
    }
}
