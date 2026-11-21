package org.example.service;

import org.example.model.ShortLink;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LinkService {

    private final Map<String, ShortLink> links = new HashMap<>();
    private final ShortCodeGenerator generator = new ShortCodeGenerator();

    public ShortLink create(
            String originalUrl,
            UUID ownerId,
            int clickLimit
    ) {
        String code;

        do {
            code = generator.generate();
        } while (links.containsKey(code));

        ShortLink shortLink = new ShortLink(originalUrl, code, ownerId, clickLimit, Instant.now());
        links.put(code, shortLink);
        return shortLink;
    }

    public ShortLink getByCode(String code) {
        ShortLink link = links.get(code);

        if (link == null) {
            throw new IllegalArgumentException("Link not found: " + code);
        }

        return link;
    }

    public List<ShortLink> listByOwner(UUID userId) {
        return this.links.values().stream()
                .filter(link -> link.getOwnerId().equals(userId))
                .toList();
    }

    public void updateLimit(String code, UUID ownerId, int newLimit) {
        ShortLink link = getByCode(code);

        if (!link.getOwnerId().equals(ownerId)) {
            throw new IllegalArgumentException("Access denied");
        }

        link.updateClickLimit(newLimit);
    }

    public void delete(String code, UUID ownerId) {
        ShortLink link = getByCode(code);

        if (!link.getOwnerId().equals(ownerId)) {
            throw new IllegalArgumentException("Access denied");
        }

        links.remove(code);
    }
}
