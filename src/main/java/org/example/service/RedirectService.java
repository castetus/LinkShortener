package org.example.service;

import org.example.model.ShortLink;

import java.time.Instant;

public class RedirectService {
    private final LinkService linkService;

    public RedirectService(LinkService linkService) {
        this.linkService = linkService;
    }

    public String open(String code) {
        ShortLink link = linkService.getByCode(code);

        if (link.isExpired(Instant.now())) {
            throw new IllegalStateException("Link has expired");
        }

        link.incrementClickCount();

        return link.getOriginalUrl();
    }
}
