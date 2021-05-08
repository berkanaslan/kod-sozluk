package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.BaseEntity;
import com.berkanaslan.eksisozlukclone.repository.BaseEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseEntityController<T extends BaseEntity> {

    public abstract Class<T> getEntityClass();

    public abstract String getRequestPath();

    @Autowired
    private BaseEntityRepository<T> baseEntityRepository;

    public BaseEntityRepository<T> getBaseEntityRepository() {
        return baseEntityRepository;
    }

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
