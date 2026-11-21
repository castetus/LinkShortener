package org.example.model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ShortLinkTest {

    private ShortLink createTestLink () {
        return new ShortLink(
                "https://example.com",
                "abc123",
                UUID.randomUUID(),
                10,
                Instant.now()
        );
    }

    private ShortLink createTestLink(Instant createdAt) {
        return new ShortLink(
                "https://example.com",
                "abc123",
                UUID.randomUUID(),
                3,
                createdAt
        );
    }

    @Test
    void shouldIncrementClickCount() {
        ShortLink link = this.createTestLink();

        link.incrementClickCount();
        link.incrementClickCount();

        assertEquals(2, link.getClickCount());
    }

    @Test
    void shouldUpdateClickLimit() {
        ShortLink link = this.createTestLink();

        link.updateClickLimit(20);

        assertEquals(20, link.getClickLimit());
    }

    @Test
    void shouldRejectLimitLowerThanClickCount() {
        ShortLink link = createTestLink();

        link.incrementClickCount();
        link.incrementClickCount();

        assertThrows(
                IllegalArgumentException.class,
                () -> link.updateClickLimit(1)
        );
    }

    @Test
    void shouldNotBeExpiredBeforeTtl() {
        Instant createdAt = Instant.parse("2026-01-01T12:00:00Z");

        ShortLink link = this.createTestLink(createdAt);

        assertFalse(link.isExpired(
                createdAt.plus(Duration.ofHours(23))
        ));
    }

    @Test
    void shouldBeExpiredWhenTtlReached() {
        Instant createdAt = Instant.parse("2026-01-01T12:00:00Z");

        ShortLink link = this.createTestLink(createdAt);

        assertTrue(link.isExpired(
                createdAt.plus(Duration.ofHours(24))
        ));
    }
}
