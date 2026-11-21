package org.example.model;

import org.example.model.ShortLink;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}
