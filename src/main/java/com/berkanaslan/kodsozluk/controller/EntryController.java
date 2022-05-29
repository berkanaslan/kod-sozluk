package com.berkanaslan.kodsozluk.controller;

import com.berkanaslan.kodsozluk.model.Entry;
import com.berkanaslan.kodsozluk.service.entry.EntryAddedEvent;
import com.berkanaslan.kodsozluk.service.entry.EntryService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping(path = EntryController.PATH)
@AllArgsConstructor
public class EntryController extends BaseEntityController<Entry, Entry.Info> {
    static final String PATH = "/entry";

    private final EntryService entryService;

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

    @PostMapping
    @Override
    public Entry save(@RequestBody final Entry entry) {
        return entryService.save(entry);
    }

    @GetMapping(path = "topic/{topicId}", params = {"pn", "ps", "sb", "sd"})
    public Page<Entry.Info> getPagedEntriesByTopicId(
            @PathVariable(name = "topicId") long topicId,
            @RequestParam(name = "pn", defaultValue = "0", required = false) int page,
            @RequestParam(name = "ps", defaultValue = "20", required = false) int size,
            @RequestParam(name = "sb", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sd", defaultValue = SORT_DIRECTION_ASC, required = false) String sortDirection) {

        final Pageable pageable = preparePageRequest(page, size, sortBy, sortDirection);
        return entryService.getPagedEntriesByTopicId(topicId, pageable);
    }

    @GetMapping(path = "/user/{userId}", params = {"pn", "ps", "sb", "sd"})
    public Page<Entry.Info> getPagedEntriesOfUser(
            @PathVariable(name = "userId") long userId,
            @RequestParam(name = "pn", defaultValue = "0", required = false) int page,
            @RequestParam(name = "ps", defaultValue = "20", required = false) int size,
            @RequestParam(name = "sb", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sd", defaultValue = SORT_DIRECTION_DESC, required = false) String sortDirection) {

        final Pageable pageable = preparePageRequest(page, size, sortBy, sortDirection);
        return entryService.getPagedEntriesOfUser(userId, pageable);
    }

    @GetMapping(path = "/user/{userId}/favorites", params = {"pn", "ps", "sb", "sd"})
    public Page<Entry.Info> getPagedFavoritedEntriesOfUser(
            @PathVariable(name = "userId") long userId,
            @RequestParam(name = "pn", defaultValue = "0", required = false) int page,
            @RequestParam(name = "ps", defaultValue = "20", required = false) int size,
            @RequestParam(name = "sb", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sd", defaultValue = SORT_DIRECTION_DESC, required = false) String sortDirection) {

        final Pageable pageable = preparePageRequest(page, size, sortBy, sortDirection);
        return entryService.getPagedFavoritedEntriesOfUser(userId, pageable);
    }

    @GetMapping(path = "/{entryId}/add-to-favorite")
    public boolean addToFavorite(@PathVariable(name = "entryId") long entryId) {
        return entryService.addToFavorites(entryId);
    }
}
