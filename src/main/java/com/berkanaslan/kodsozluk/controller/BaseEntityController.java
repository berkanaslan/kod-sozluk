package com.berkanaslan.kodsozluk.controller;

import com.berkanaslan.kodsozluk.model.Principal;
import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.model.core.BaseEntity;
import com.berkanaslan.kodsozluk.repository.BaseEntityRepository;
import com.berkanaslan.kodsozluk.util.I18NUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseEntityController<T extends BaseEntity, I extends BaseEntity.Info> {
    public static final String SORT_DIRECTION_ASC = "a";
    public static final String SORT_DIRECTION_DESC = "d";

    public abstract Class<T> getEntityClass();

    public abstract Class<I> getEntityInfoClass();

    public abstract String getRequestPath();

    @Autowired
    private BaseEntityRepository<T> baseEntityRepository;

    public BaseEntityRepository<T> getBaseEntityRepository() {
        return baseEntityRepository;
    }

    // -------------------------------------------------------------------------
    // GET - GET INFO - GET ALL - GET ALL PAGED                                /
    // -------------------------------------------------------------------------
    @GetMapping("/all")
    public List<T> getAll() {
        return baseEntityRepository.findAll();
    }

    @GetMapping(params = {"pn", "ps", "sb", "sd"})
    public Page<T> getAllPaged(
            @RequestParam(name = "pn", defaultValue = "0", required = false) int page,
            @RequestParam(name = "ps", defaultValue = "20", required = false) int size,
            @RequestParam(name = "sb", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "sd", defaultValue = SORT_DIRECTION_ASC, required = false) String sortDirection) {
        final Pageable pageable = preparePageRequest(page, size, sortBy, sortDirection);
        return baseEntityRepository.findAll(pageable);
    }

    @GetMapping(path = "/i")
    public List<I> getAllInfo() {
        return baseEntityRepository.findAllBy(getEntityInfoClass(), Sort.by("id"));
    }

    @GetMapping(path = "/i", params = {"pn", "ps", "sb", "sd"})
    public Page<I> getAllInfoPaged(@RequestParam(name = "pn", defaultValue = "0", required = false) int page,
                                   @RequestParam(name = "ps", defaultValue = "20", required = false) int size,
                                   @RequestParam(name = "sb", defaultValue = "id", required = false) String sortBy,
                                   @RequestParam(name = "sd", defaultValue = SORT_DIRECTION_ASC, required = false) String sortDirection) {
        final Pageable pageable = preparePageRequest(page, size, sortBy, sortDirection);
        return baseEntityRepository.findAllPagedBy(pageable, getEntityInfoClass());
    }

    @GetMapping("{id}")
    public T getById(@PathVariable(name = "id") long id) {
        Optional<T> optionalT = baseEntityRepository.findById(id);

        if (optionalT.isPresent()) {
            return optionalT.get();
        }

        throw new RuntimeException(String.format(I18NUtil.getMessageByLocale("message.no_such"), getEntityClass().getSimpleName()));
    }

    // -------------------------------------------------------------------------
    // CREATE - UPDATE                                                         /
    // -------------------------------------------------------------------------
    @PostMapping
    public T save(@RequestBody T t) {
        return baseEntityRepository.save(t);
    }

    // -------------------------------------------------------------------------
    // DELETE                                                                  /
    // -------------------------------------------------------------------------
    @DeleteMapping("{id}")
    public void delete(@PathVariable(name = "id") long id) {
        final Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String username = principal.getUsername();

        if (username == null || !username.equals(User.SUPER_ADMIN_USERNAME))
            throw new SecurityException("bu işlem sizi aşar.");

        baseEntityRepository.deleteById(id);
    }

    public static PageRequest preparePageRequest(int page, int size, String sortBy, String sortDirection) {
        String[] fields = sortBy.split(",");
        String[] directions = !Objects.isNull(sortDirection) ? sortDirection.split(",") : new String[0];

        List<Sort.Order> orders = new ArrayList<>();

        for (int i = 0; i < fields.length; i++) {
            Sort.Order order;

            if (Objects.nonNull(directions[i]) && SORT_DIRECTION_DESC.equals(directions[i])) {
                order = Sort.Order.desc(fields[i]);
            } else {
                order = Sort.Order.asc(fields[i]);
            }

            orders.add(order);
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }

    /**
     * Using with this method, the @NotNull signs properties of the object are detected and populated with the previous data.
     * <p> Example:
     * <p>
     * final Optional<User> userOptional = userRepository.getById(user.getId()); <br>
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
