package org.example.service;

import org.example.model.ShortLink;

import java.time.Instant;
import java.util.UUID;

public class LinkService {

    public ShortLink create(
            String originalUrl,
            UUID ownerId,
            int clickLimit
    ) {
        String code = new ShortCodeGenerator().generate();
        return new ShortLink(originalUrl, code, ownerId, clickLimit, Instant.now());
    }
}
