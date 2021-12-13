package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.Entry;
import com.berkanaslan.eksisozlukclone.model.Principal;
import com.berkanaslan.eksisozlukclone.model.Title;
import com.berkanaslan.eksisozlukclone.model.User;
import com.berkanaslan.eksisozlukclone.model.dto.EntryDTO;
import com.berkanaslan.eksisozlukclone.repository.EntryRepository;
import com.berkanaslan.eksisozlukclone.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = EntryController.PATH)
public class EntryController extends BaseEntityController<Entry> {

    static final String PATH = "entry";

    @Autowired
    private UserController userController;

    @Autowired
    EntryService entryService;

    @Autowired
    TitleController titleController;

    @Override
    public Class<Entry> getEntityClass() {
        return Entry.class;
    }

    @Override
    public String getRequestPath() {
        return PATH;
    }

    @Override
    public Entry save(@RequestBody Entry entry) {
        if (entry.getComment() == null)
            throw new RuntimeException("Entry can not be null!");


        Title title = titleController.findById(entry.getTitle().getId());

        if (title == null)
            throw new RuntimeException("Title not found!");

        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userController.findById(principal.getUserId());

        entry.setUser(user);

        if (entry.getId() == 0)
            entry.setCreatedAt(new Date());
        else
            entry.setUpdatedAt(new Date());

        return super.save(entry);
    }

    @GetMapping(path = "/user/{userId}")
    public List<EntryDTO> findAllByUserId(@PathVariable(value = "userId") long userId) {
        return ((EntryRepository) getBaseEntityRepository()).findAllByUserId(userId)
                .stream().map(entryService::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping(path = "/title/{titleId}")
    public List<EntryDTO> findAllByTitleId(@PathVariable(value = "titleId") long titleId) {
        return ((EntryRepository) getBaseEntityRepository()).findAllByTitleId(titleId)
                .stream().map(entryService::convertToDTO).collect(Collectors.toList());
    }


    @GetMapping(path = "/title/{titleId}/user/{userId}")
    public List<EntryDTO> findAllByTitleIdAndUserId(@PathVariable(value = "titleId") long titleId,
                                                    @PathVariable(value = "userId") long userId) {
        return ((EntryRepository) getBaseEntityRepository()).findAllByTitleIdAndUserId(titleId, userId)
                .stream().map(entryService::convertToDTO).collect(Collectors.toList());
    }
}
