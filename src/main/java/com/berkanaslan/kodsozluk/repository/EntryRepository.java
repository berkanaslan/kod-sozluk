package com.berkanaslan.kodsozluk.repository;

import com.berkanaslan.kodsozluk.model.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends BaseEntityRepository<Entry> {
    Page<Entry.Info> findAllByTopicId(long topicId, Pageable pg);

    Page<Entry.Info> findAllByAuthor_Id(long authorId, Pageable pg);

    Page<Entry.Info> findAllByFavorites_User_IdOrderByFavorites_AddedAtDesc(long authorId, Pageable pg);

    @Deprecated
    @Query(value = "SELECT IIF(EXISTS(select f.* from user_table u, favorite f" +
            " where f.entry_id = :entryId and u.id = :userId), CAST(1 AS BIT), CAST(0 AS BIT))", nativeQuery = true)
    boolean isFavoritedByClient(long entryId, long userId);

    @Query(value = "select f.entry_id from favorite f" +
            " where f.user_id = :userId and f.entry_id in :entryIds", nativeQuery = true)
    List<Long> getFavoritedByClientEntryIds(long userId, List<Long> entryIds);
}
