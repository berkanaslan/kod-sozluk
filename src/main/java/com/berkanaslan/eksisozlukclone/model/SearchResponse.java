package com.berkanaslan.eksisozlukclone.model;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponse {
    private String title;
    private String type;
    private String path;

    public SearchResponse() {
    }

    public SearchResponse(String title, String type, String path) {
        this.title = title;
        this.type = type;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
