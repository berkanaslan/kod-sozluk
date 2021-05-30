package com.berkanaslan.eksisozlukclone.repository;

import com.berkanaslan.eksisozlukclone.model.Title;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends BaseEntityRepository<Title> {
    List<Title> findTop10ByNameContains(String query);
}
