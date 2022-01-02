package com.berkanaslan.kodsozluk.model;

import com.berkanaslan.kodsozluk.audit.Auditable;
import com.berkanaslan.kodsozluk.config.LowerCase;
import com.berkanaslan.kodsozluk.model.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "entry_favorites", joinColumns = @JoinColumn(name = "entry_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> favorites;

    private int favoritesCount;

    @PrePersist
    @PreUpdate
    public void preOperations() {
        setMessage(getMessage().toLowerCase(Locale.ROOT));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public interface Info extends BaseEntity.Info {
        String getMessage();

        String getCreatedBy();

        Date getCreationDate();

        Date getLastModifiedDate();

        int getFavoritesCount();
    }
}
