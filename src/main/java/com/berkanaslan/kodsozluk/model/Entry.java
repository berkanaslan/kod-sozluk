package com.berkanaslan.kodsozluk.model;

import com.berkanaslan.kodsozluk.audit.Auditable;
import com.berkanaslan.kodsozluk.config.LowerCase;
import com.berkanaslan.kodsozluk.model.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class Entry extends Auditable implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @LowerCase
    @Column(nullable = false, length = 2048)
    private String message;

    @ManyToOne
    private Topic topic;

    @ManyToOne
    private User author;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(name = "entry_favorite_relation")
    private Set<EntryFavorite> favorites = new HashSet<>();

    @Column(nullable = false)
    private long favoritesCount;

    @PrePersist
    @PreUpdate
    public void preOperations() {
        setMessage(getMessage().toLowerCase(Locale.ROOT));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public interface Info extends BaseEntity.Info {
        Topic getTopic();

        String getMessage();

        User getAuthor();

        Date getCreationDate();

        Date getLastModifiedDate();

        long getFavoritesCount();
    }
}
