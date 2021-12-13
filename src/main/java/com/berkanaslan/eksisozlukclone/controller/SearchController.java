package com.berkanaslan.eksisozlukclone.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = SearchController.PATH)
public class SearchController {
    final static String PATH = "/search";

}
