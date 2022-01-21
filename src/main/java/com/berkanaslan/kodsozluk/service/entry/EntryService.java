package com.berkanaslan.kodsozluk.service.entry;

import com.berkanaslan.kodsozluk.model.Entry;
import com.berkanaslan.kodsozluk.model.Principal;
import com.berkanaslan.kodsozluk.model.User;
import com.berkanaslan.kodsozluk.repository.EntryRepository;
import com.berkanaslan.kodsozluk.repository.UserRepository;
import com.berkanaslan.kodsozluk.util.I18NUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EntryService {
    private final EntryRepository entryRepository;
    private final UserRepository userRepository;

    /**
     * If, entry is already favorited by the same user,
     * entry will removed from the favorites for the user.
     *
     * @param entryId
     */
    public void addToFavorites(final long entryId) {
        final Entry entry = entryRepository.findById(entryId).orElse(null);

        if (entry == null) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.no_such", Entry.class.getSimpleName()));
        }

        final Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userRepository.findByUsername(principal.getUsername()).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException(I18NUtil.getMessageByLocale("message.no_such", User.class.getSimpleName()));
        }

        final List<User> favorites = entry.getFavorites();

        if (favorites.contains(user)) {
            favorites.remove(user);
            entry.setFavoritesCount(entry.getFavoritesCount() - 1);
        } else {
            favorites.add(user);
            entry.setFavoritesCount(entry.getFavoritesCount() + 1);
        }

        entry.setFavorites(favorites);
        entryRepository.save(entry);
    }
}
