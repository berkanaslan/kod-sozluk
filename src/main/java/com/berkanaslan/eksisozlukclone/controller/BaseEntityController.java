package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.BaseEntity;
import com.berkanaslan.eksisozlukclone.repository.BaseEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public abstract class BaseEntityController<T extends BaseEntity> {
    public static final String SORT_DIRECTION_ASC = "a";
    public static final String SORT_DESC = "d";

    public abstract Class<T> getEntityClass();

    public abstract String getRequestPath();

    @Autowired
    private BaseEntityRepository<T> baseEntityRepository;

    // Get all
    @GetMapping
    public List<T> findAll() {
        return baseEntityRepository.findAll();
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
    public T save(@RequestBody T t) {
        baseEntityRepository.save(t);
        return t;
    }

    // Delete
    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") long id) {
        baseEntityRepository.deleteById(id);
    }
}
