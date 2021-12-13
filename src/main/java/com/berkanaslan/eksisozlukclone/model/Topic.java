package com.berkanaslan.eksisozlukclone.model;

import com.berkanaslan.eksisozlukclone.audit.Auditable;
import com.berkanaslan.eksisozlukclone.model.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(indexes = {@Index(columnList = "name"), @Index(columnList = "id, name")})
public class Topic extends Auditable implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "topic", targetEntity = Entry.class)
    private List<Entry> entries;

    public Topic() {
    }

    public Topic(String name, List<Entry> entries) {
        this.name = name;
        this.entries = entries;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "Topic{" + "id=" + id + ", name='" + name + '\'' + ", entries=" + entries + '}';
    }
}

