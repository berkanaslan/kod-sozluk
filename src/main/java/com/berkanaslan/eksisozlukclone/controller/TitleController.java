package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.Title;
import com.berkanaslan.eksisozlukclone.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(name = TitleController.PATH)
public class TitleController extends BaseEntityController<Title> {

    static final String PATH = "title";

    @Autowired
    TitleRepository titleRepository;

    @Override
    public Class<Title> getEntityClass() {
        return Title.class;
    }

    @Override
    public String getRequestPath() {
        return PATH;
    }

    @GetMapping
    @Override
    public List<Title> findAll() {
        return titleRepository.findAll();
    }
}
