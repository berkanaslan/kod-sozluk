package com.berkanaslan.eksisozlukclone.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "titles")
public class Title extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    public Title() {
    }

    public Title(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
