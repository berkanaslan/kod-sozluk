package com.berkanaslan.eksisozlukclone.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface BaseEntity {
    long getId();
}
