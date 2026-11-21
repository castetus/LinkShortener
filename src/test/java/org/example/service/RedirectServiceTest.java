package org.example.service;

import org.example.model.ShortLink;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class RedirectServiceTest {
    @Test
    void shouldReturnOriginalUrlAndIncrementClickCount() {
        LinkService linkService = new LinkService();
        RedirectService redirectService = new RedirectService(linkService);

        ShortLink link = linkService.create(
                "https://example.com",
                UUID.randomUUID(),
                3
        );

        String originalUrl = redirectService.open(link.getCode());

        assertEquals("https://example.com", originalUrl);
        assertEquals(1, link.getClickCount());
    }
}
