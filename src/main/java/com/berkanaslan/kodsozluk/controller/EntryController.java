package com.berkanaslan.kodsozluk.controller;

import com.berkanaslan.kodsozluk.model.Entry;
import com.berkanaslan.kodsozluk.repository.EntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping(path = "topic/{topicId}", params = {"pn", "ps", "sb", "sd"})
    public Page<Entry.Info> getPagedEntriesByTopicId(
            @PathVariable(name = "topicId") long topicId,
            @RequestParam(name = "pn", defaultValue = "0", required = false) int page,
            @RequestParam(name = "ps", defaultValue = "20", required = false) int size,
            @RequestParam(name = "sb", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sd", defaultValue = SORT_DIRECTION_ASC, required = false) String sortDirection) {

        final Pageable pageable = preparePageRequest(page, size, sortBy, sortDirection);
        return ((EntryRepository) getBaseEntityRepository()).findAllByTopicIdOrderByIdAsc(topicId, pageable);
    }
}
