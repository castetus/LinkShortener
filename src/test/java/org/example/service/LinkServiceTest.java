package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.example.model.ShortLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkServiceTest {

    private LinkService linkService;

    @BeforeEach
    void setUp() {
        linkService = new LinkService();
    }

    @Test
    void shouldCreateLinkWithProvidedData() {
        UUID ownerId = UUID.randomUUID();

        ShortLink link = linkService.create("https://example.com/article", ownerId, 10);

        assertEquals("https://example.com/article", link.getOriginalUrl());
        assertEquals(ownerId, link.getOwnerId());
        assertEquals(10, link.getClickLimit());
    }

    @Test
    void shouldInitializeClickCountToZero() {

        ShortLink link = linkService.create("https://example.com", UUID.randomUUID(), 10);

        assertEquals(0, link.getClickCount());
    }

    @Test
    void shouldDeleteExpiredLink() {
        ShortLink link = linkService.create("https://example.com", UUID.randomUUID(), 3);

        Instant now = link.getCreatedAt().plus(Duration.ofHours(25));

        linkService.removeExpired(now);

        assertThrows(IllegalArgumentException.class, () -> linkService.getByCode(link.getCode()));
    }

    @Test
    void shouldKeepActiveLink() {
        ShortLink link = linkService.create("https://example.com", UUID.randomUUID(), 3);

        Instant now = link.getCreatedAt().plus(Duration.ofHours(23));

        linkService.removeExpired(now);

        assertSame(link, linkService.getByCode(link.getCode()));
    }

    @Test
    void shouldDeleteLinkExactlyAtExpiration() {
        ShortLink link = linkService.create("https://example.com", UUID.randomUUID(), 3);

        Instant now = link.getCreatedAt().plus(Duration.ofHours(24));

        linkService.removeExpired(now);

        assertThrows(IllegalArgumentException.class, () -> linkService.getByCode(link.getCode()));
    }
}
