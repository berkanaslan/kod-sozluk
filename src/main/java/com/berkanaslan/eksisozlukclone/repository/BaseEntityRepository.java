package com.berkanaslan.eksisozlukclone.repository;

import com.berkanaslan.eksisozlukclone.model.core.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
    <V> List<V> findAllBy(Class<V> type, Sort sort);

    <V> Page<V> findAllPagedBy(Pageable pageable, Class<V> type);
}
