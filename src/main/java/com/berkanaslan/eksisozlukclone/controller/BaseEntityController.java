package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.BaseEntity;
import com.berkanaslan.eksisozlukclone.repository.BaseEntityRepository;
import com.berkanaslan.eksisozlukclone.util.ExceptionMessageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    @GetMapping("/all")
    public List<T> findAll() {
        return baseEntityRepository.findAll();
    }

    // Get all with paged
    @GetMapping
    public Page<T> findAllPaged(@PageableDefault(size = 20, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return baseEntityRepository.findAll(pageable);
    }

    // Get single
    @GetMapping("{id}")
    public T findById(@PathVariable(name = "id") long id) {
        Optional<T> optionalT = baseEntityRepository.findById(id);

        if (optionalT.isPresent()) {
            return optionalT.get();
        }

        throw new RuntimeException(ExceptionMessageUtil.getMessageByLocale("message.no_such") + " " + getEntityClass().getSimpleName());
    }

    // Create || Update
    @PostMapping
    public T save(@RequestBody T t) {
        return baseEntityRepository.save(t);
    }

    // Delete
    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") long id) {
        baseEntityRepository.deleteById(id);
    }

    /**
     * Using with this method, the @NotNull signs properties of the object are detected and populated with the previous data.
     * Example:
     * final Optional<User> userOptional = userRepository.getById(user.getId());
     * final User existingUserOnDB = userOptional.get();
     * <p>
     * copyNonNullProperties(user, existingUserOnDB);
     * return userRepository.save(existingUserOnDB);
     */
    public static void copyNonNullProperties(Object src, Object target, String... ignoreProperties) {
        String[] nullPropertyNames = getNullPropertyNames(src);
        if (ignoreProperties != null && ignoreProperties.length > 0) {
            List<String> nullPropertyNameList = Arrays.stream(nullPropertyNames).collect(Collectors.toList());

            nullPropertyNameList.addAll(Arrays.stream(ignoreProperties).collect(Collectors.toList()));
            nullPropertyNames = nullPropertyNameList.toArray(String[]::new);
        }

        BeanUtils.copyProperties(src, target, nullPropertyNames);

        System.out.println(Arrays.toString(nullPropertyNames));
    }

    protected static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];

        return emptyNames.toArray(result);
    }
}
