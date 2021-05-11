package com.berkanaslan.eksisozlukclone.controller;


import com.berkanaslan.eksisozlukclone.model.Entry;
import com.berkanaslan.eksisozlukclone.model.Principal;
import com.berkanaslan.eksisozlukclone.model.User;
import com.berkanaslan.eksisozlukclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping(path = EntryController.PATH)
public class EntryController extends BaseEntityController<Entry> {

    static final String PATH = "entry";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserController userController;

    @Override
    public Class<Entry> getEntityClass() {
        return Entry.class;
    }

    @Override
    public String getRequestPath() {
        return PATH;
    }

    @Override
    public long save(@RequestBody Entry entry) {
        if (entry.getEntry() == null) {
            throw new RuntimeException("Entry can not be null!");
        }

        if (entry.getId() == 0) {
            entry.setCreatedDate(new Date());
        } else {
            entry.setUpdatedDate(new Date());
        }

        return super.save(entry);
    }
}
