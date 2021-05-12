package com.berkanaslan.eksisozlukclone.repository;

import com.berkanaslan.eksisozlukclone.model.Entry;

import java.util.List;

public interface EntryRepository extends BaseEntityRepository<Entry> {
    List<Entry> findAllByUserId(long userId);

    List<Entry> findAllByTitleId(long userId);

    List<Entry> findAllByTitleIdAndUserId(long titleId, long userId);
}
