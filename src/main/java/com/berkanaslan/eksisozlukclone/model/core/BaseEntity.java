package com.berkanaslan.eksisozlukclone.model.core;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface BaseEntity {
    long getId();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    interface Info {
        long getId();
    }
}
