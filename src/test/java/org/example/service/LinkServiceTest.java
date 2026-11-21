
package org.example.service;

import org.example.model.ShortLink;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkServiceTest {

    @Test
    void shouldCreateLinkWithProvidedData() {
        LinkService service = new LinkService();
        UUID ownerId = UUID.randomUUID();

        ShortLink link = service.create(
                "https://example.com/article",
                ownerId,
                10
        );

        assertEquals(
                "https://example.com/article",
                link.getOriginalUrl()
        );
        assertEquals(ownerId, link.getOwnerId());
        assertEquals(10, link.getClickLimit());
    }

    @Test
    void shouldInitializeClickCountToZero() {
        LinkService service = new LinkService();

        ShortLink link = service.create(
                "https://example.com",
                UUID.randomUUID(),
                10
        );

        assertEquals(0, link.getClickCount());
    }
}
