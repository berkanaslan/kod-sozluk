package com.berkanaslan.eksisozlukclone.model;

import com.berkanaslan.eksisozlukclone.audit.Auditable;
import com.berkanaslan.eksisozlukclone.model.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table
public class Entry extends Auditable implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne(targetEntity = Topic.class)
    private Topic topic;

    @Column(nullable = false, length = 2048)
    private String message;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> favorites;

    private int favoritesCount;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<User> favorites) {
        this.favorites = favorites;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    @Override
    public String toString() {
        return "Entry{" + "id=" + id + ", topic=" + topic + ", message='" + message + '\'' + ", favoritesCount=" + favoritesCount + '}';
    }
}
