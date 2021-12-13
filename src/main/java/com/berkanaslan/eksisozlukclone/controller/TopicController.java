package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.Topic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = TopicController.PATH)
public class TopicController extends BaseEntityController<Topic> {
    static final String PATH = "/topic";

    @Override
    public Class<Topic> getEntityClass() {
        return Topic.class;
    }

    @Override
    public String getRequestPath() {
        return PATH;
    }

}
