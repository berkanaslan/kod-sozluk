package com.berkanaslan.kodsozluk.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class EntryFavoriteId implements Serializable {
    private static final long serialVersionUID = -3914688550620060042L;

    @Column(name = "entry_id")
    private long entryId;

    @Column(name = "user_id")
    private long userId;

    public EntryFavoriteId(long entryId, long userId) {
        this.entryId = entryId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EntryFavoriteId that = (EntryFavoriteId) o;
        return Objects.equals(entryId, that.entryId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryId, userId);
    }
}
