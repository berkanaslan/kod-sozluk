package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.Entry;
import com.berkanaslan.eksisozlukclone.model.Principal;
import com.berkanaslan.eksisozlukclone.model.User;
import com.berkanaslan.eksisozlukclone.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(path = EntryController.PATH)
public class EntryController extends BaseEntityController<Entry> {

    static final String PATH = "entry";

    @Autowired
    private UserController userController;

    @Autowired
    private TitleController titleController;

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
        if (entry.getComment() == null)
            throw new RuntimeException("Entry can not be null!");

        if (entry.getTitle() == null)
            throw new RuntimeException("Title can not be null!");

        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userController.findById(principal.getUserId());

        entry.setUser(user);

        if (entry.getId() == 0)
            entry.setCreatedAt(new Date());
        else
            entry.setUpdatedAt(new Date());

        super.save(entry);

        return entry.getId();
    }

    @GetMapping(path = "/user/{userId}")
    public List<Entry> findAllByUserId(@PathVariable(value = "userId") long userId) {
        return ((EntryRepository) getBaseEntityRepository()).findAllByUserId(userId);
    }

    @GetMapping(path = "/title/{titleId}")
    public List<Entry> findAllByTitleId(@PathVariable(value = "titleId") long titleId) {
        return ((EntryRepository) getBaseEntityRepository()).findAllByTitleId(titleId);
    }


    @GetMapping(path = "/title/{titleId}/user/{userId}")
    public List<Entry> findAllByTitleIdAndUserId(@PathVariable(value = "titleId") long titleId,
                                                 @PathVariable(value = "userId") long userId) {
        return ((EntryRepository) getBaseEntityRepository()).findAllByTitleIdAndUserId(titleId, userId);
    }

}
