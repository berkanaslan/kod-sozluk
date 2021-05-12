package com.berkanaslan.eksisozlukclone.model.dto;

import com.berkanaslan.eksisozlukclone.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonTypeName;


public class UserDTO extends BaseEntity {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
