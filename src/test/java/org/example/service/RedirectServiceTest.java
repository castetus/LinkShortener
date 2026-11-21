
package org.example.service;

import org.example.model.ShortLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RedirectServiceTest {

    private LinkService linkService;
    private RedirectService redirectService;

    @BeforeEach
    void setUp() {
        linkService = new LinkService();
        redirectService = new RedirectService(linkService);
    }

    @Test
    void shouldReturnOriginalUrlAndIncrementClickCount() {
        ShortLink link = linkService.create(
                "https://example.com",
                UUID.randomUUID(),
                3
        );

        String url = redirectService.open(link.getCode());

        assertEquals("https://example.com", url);
        assertEquals(1, link.getClickCount());
    }

    @Test
    void shouldAllowClicksUpToLimit() {
        ShortLink link = linkService.create(
                "https://example.com",
                UUID.randomUUID(),
                2
        );

        redirectService.open(link.getCode());
        redirectService.open(link.getCode());

        assertEquals(2, link.getClickCount());
    }

    @Test
    void shouldRejectClickWhenLimitExceeded() {
        ShortLink link = linkService.create(
                "https://example.com",
                UUID.randomUUID(),
                1
        );

        redirectService.open(link.getCode());

        assertThrows(
                IllegalStateException.class,
                () -> redirectService.open(link.getCode())
        );

        assertEquals(1, link.getClickCount());
    }

    @Test
    void shouldRejectUnknownCode() {
        assertThrows(
                IllegalArgumentException.class,
                () -> redirectService.open("unknown")
        );
    }
}
