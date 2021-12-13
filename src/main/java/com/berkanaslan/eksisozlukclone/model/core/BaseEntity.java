package com.berkanaslan.eksisozlukclone.model.core;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface BaseEntity {
    long getId();
}
