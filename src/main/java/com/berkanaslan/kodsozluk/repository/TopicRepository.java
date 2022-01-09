package com.berkanaslan.kodsozluk.repository;

import com.berkanaslan.kodsozluk.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TopicRepository extends BaseEntityRepository<Topic> {
    Optional<Topic> findByName(String name);

    // TODO : If possible, move to HQL/JPA from native query.
    @Query(value = "select * from topic t" +
            " where cast(creation_date as date) = cast(:date as date)" +
            " order by daily_total_entry_count desc", nativeQuery = true)
    Page<Topic> findAllPagedByCreationDateOrderByDailyTotalEntryCountDesc(Date date, Pageable pageable);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Topic t set t.dailyTotalEntryCount = 0 where 1 = 1")
    void resetDailyTotalEntryCount();
}


