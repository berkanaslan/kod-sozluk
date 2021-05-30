package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.Entry;
import com.berkanaslan.eksisozlukclone.model.SearchResponse;
import com.berkanaslan.eksisozlukclone.model.Title;
import com.berkanaslan.eksisozlukclone.model.User;
import com.berkanaslan.eksisozlukclone.repository.EntryRepository;
import com.berkanaslan.eksisozlukclone.repository.TitleRepository;
import com.berkanaslan.eksisozlukclone.repository.UserRepository;
import com.berkanaslan.eksisozlukclone.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = SearchController.PATH)
public class SearchController {
    final static String PATH = "search";

    @Autowired
    UserRepository userRepository;

    @Autowired
    TitleRepository titleRepository;

    @GetMapping
    public List<SearchResponse> getSearchResults(@RequestParam String query) {
        List<SearchResponse> searchResponses = new ArrayList<>();

        List<User> users = userRepository.findTop10ByUsernameContains(query);
        List<Title> titles = titleRepository.findTop10ByNameContains(query);

        if (users != null) {
            for (User user : users) {
                searchResponses.add(new SearchResponse(user.getUsername(), "User", "user/" + user.getId()));
            }
        }

        if (titles != null) {
            for (Title title : titles) {
                searchResponses.add(new SearchResponse(title.getName(), "Title", "title/" + title.getId()));
            }
        }

        return searchResponses;
    }

}
