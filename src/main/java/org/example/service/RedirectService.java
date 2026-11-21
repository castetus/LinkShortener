package org.example.service;

import org.example.model.ShortLink;

public class RedirectService {
    private final LinkService linkService;

    public RedirectService(LinkService linkService) {
        this.linkService = linkService;
    }

    public String open(String code) {
        ShortLink link = linkService.getByCode(code);

        link.incrementClickCount();

        return link.getOriginalUrl();
    }
}
