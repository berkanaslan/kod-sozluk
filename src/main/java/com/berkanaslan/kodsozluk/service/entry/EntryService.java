package com.berkanaslan.kodsozluk.service.entry;

import com.berkanaslan.kodsozluk.model.*;
import com.berkanaslan.kodsozluk.model.core.BaseEntity;
import com.berkanaslan.kodsozluk.repository.EntryRepository;
import com.berkanaslan.kodsozluk.repository.TopicRepository;
import com.berkanaslan.kodsozluk.repository.UserRepository;
import com.berkanaslan.kodsozluk.service.PrincipalService;
import com.berkanaslan.kodsozluk.util.I18NUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EntryService {
    private final EntryRepository entryRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final PrincipalService principalService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Entry save(Entry entry) {
        if (entry.getTopic().getId() == 0) {
            final Topic savedTopic = topicRepository.save(entry.getTopic());
            entry.setTopic(savedTopic);
        }

        if (entry.getId() == 0) {
            entry.setCreatedAt(new Date());
            applicationEventPublisher.publishEvent(new EntryAddedEvent(this, entry));
        } else {
            entry.setModifiedAt(new Date());
        }

        return entryRepository.save(entry);
    }

    public Page<Entry.Info> getPagedEntriesByTopicId(final long topicId, final Pageable pg) {
        final Page<Entry.Info> pagedEntries = entryRepository.findAllByTopicId(topicId, pg);
        final User client = principalService.getUserFromPrincipal().orElse(null);

        if (client == null) {
            return pagedEntries;
        }

        setFavoritedValueForEveryEntry(pagedEntries, client);
        return pagedEntries;
    }

    public Page<Entry.Info> getPagedEntriesOfUser(final long userId, final Pageable pg) {
        Page<Entry.Info> pagedEntries = entryRepository.findAllByAuthor_Id(userId, pg);

        final User client = principalService.getUserFromPrincipal().orElse(null);

        if (client == null) {
            return pagedEntries;
        }

        setFavoritedValueForEveryEntry(pagedEntries, client);
        return pagedEntries;
    }

    public Page<Entry.Info> getPagedFavoritedEntriesOfUser(final long userId, final Pageable pg) {
        Page<Entry.Info> pagedEntries = entryRepository.findAllByFavorites_User_IdOrderByFavorites_AddedAtDesc(userId, pg);

        final User client = principalService.getUserFromPrincipal().orElse(null);

        if (client == null) {
            return pagedEntries;
        }

        setFavoritedValueForEveryEntry(pagedEntries, client);
        return pagedEntries;
    }

    private void setFavoritedValueForEveryEntry(Page<Entry.Info> pagedEntries, User client) {
        final List<Long> favoritedByClientEntryIds = getFavoritedByClientEntryIdsInPageContent(pagedEntries, client);

        favoritedByClientEntryIds.forEach(entryId ->
                pagedEntries.getContent().stream().filter(pe -> pe.getId() == entryId).findFirst().ifPresent(e -> e.setFavorited(true))
        );
    }

    private List<Long> getFavoritedByClientEntryIdsInPageContent(Page<Entry.Info> pagedEntries, User client) {
        return entryRepository.getFavoritedByClientEntryIds(
                client.getId(), pagedEntries.getContent().stream().map(BaseEntity.Info::getId).collect(Collectors.toList())
        );
    }

    /**
     * If, entry is already favorited by the same user,
     * entry will removed from the favorites for the user.
     *
     * @param entryId
     */
    public boolean addToFavorites(final long entryId) {
        final Entry entry = entryRepository.findById(entryId).orElse(null);

        if (entry == null) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.no_such", Entry.class.getSimpleName()));
        }

        final Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userRepository.findByUsername(principal.getUsername()).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.no_such", User.class.getSimpleName()));
        }

        final EntryFavorite entryFavorite = new EntryFavorite(entry, user);

        final Set<EntryFavorite> favorites = entry.getFavorites();

        // Add to favorites
        final boolean isAdded = favorites.add(entryFavorite);

        // Or remove:
        if (!isAdded) {
            favorites.remove(entryFavorite);
            entry.setFavoritesCount(entry.getFavoritesCount() - 1);
        } else {
            entry.setFavoritesCount(entry.getFavoritesCount() + 1);
        }

        entry.setFavorites(favorites);
        entryRepository.save(entry);
        return isAdded;
    }
}
