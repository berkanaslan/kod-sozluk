package com.berkanaslan.kodsozluk.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "favorite")
@Getter
@Setter
@NoArgsConstructor
public class EntryFavorite {
    @EmbeddedId
    private EntryFavoriteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("entryId")
    private Entry entry;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    private Date addedAt = new Date();

    public EntryFavorite(Entry entry, User user) {
        this.entry = entry;
        this.user = user;
        this.id = new EntryFavoriteId(entry.getId(), user.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        EntryFavorite that = (EntryFavorite) o;
        return Objects.equals(entry, that.entry) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entry, user);
    }
}
