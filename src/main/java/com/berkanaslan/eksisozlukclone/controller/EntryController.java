package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.Entry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = EntryController.PATH)
public class EntryController extends BaseEntityController<Entry, Entry.Info> {
    static final String PATH = "/entry";

    @Override
    public Class<Entry> getEntityClass() {
        return Entry.class;
    }

    @Override
    public Class<Entry.Info> getEntityInfoClass() {
        return Entry.Info.class;
    }

    @Override
    public String getRequestPath() {
        return PATH;
    }


}
