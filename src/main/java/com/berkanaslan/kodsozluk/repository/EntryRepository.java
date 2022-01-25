package com.berkanaslan.kodsozluk.repository;

import com.berkanaslan.kodsozluk.model.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends BaseEntityRepository<Entry> {
    Page<Entry.Info> findAllByTopicId(long topicId1, Pageable pg);

    Page<Entry.Info> findAllByAuthor_Id(long authorId, Pageable pg);

    Page<Entry.Info> findAllByFavorites_User_IdOrderByFavorites_AddedAtDesc(long authorId, Pageable pg);
}
