package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.BaseEntity;
import com.berkanaslan.eksisozlukclone.repository.BaseEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public abstract class BaseEntityController<T extends BaseEntity> {

    public abstract Class<T> getEntityClass();

    public abstract String getRequestPath();

    @Autowired
    private BaseEntityRepository<T> baseEntityRepository;

    public BaseEntityRepository<T> getBaseEntityRepository() {
        return baseEntityRepository;
    }

    // Get all
    @GetMapping("/all")
    public List<T> findAll() {
        return baseEntityRepository.findAll();
    }

    // Get all with paged
    @GetMapping
    public Page<T> findAllPaged(@PageableDefault(size = 20, sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable) {
        return baseEntityRepository.findAll(pageable);
    }

    // Get single
    @GetMapping("{id}")
    public T findById(@PathVariable(name = "id") long id) {
        Optional<T> optionalT = baseEntityRepository.findById(id);
        if (optionalT.isPresent()) return optionalT.get();
        throw new RuntimeException("No such " + getEntityClass().getSimpleName());
    }

    // Create || Update
    @PostMapping
    public long save(@RequestBody T t) {
        baseEntityRepository.save(t);
        return t.getId();
    }

    // Delete
    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") long id) {
        baseEntityRepository.deleteById(id);
    }
}
