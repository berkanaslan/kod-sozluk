package com.berkanaslan.eksisozlukclone.model;

import com.berkanaslan.eksisozlukclone.audit.Auditable;
import com.berkanaslan.eksisozlukclone.config.LowerCase;
import com.berkanaslan.eksisozlukclone.model.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Locale;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(indexes = {@Index(columnList = "name"), @Index(columnList = "id, name")})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Topic extends Auditable implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @LowerCase
    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "topic", targetEntity = Entry.class)
    private List<Entry> entries;

    @PrePersist
    @PreUpdate
    public void preOperations() {
        setName(getName().toLowerCase(Locale.ROOT));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public interface Info extends BaseEntity.Info {
        String getName();
    }
}

