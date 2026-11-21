package org.example.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.example.AppConfig;
import org.example.model.ShortLink;
import org.example.service.LinkService;
import org.example.service.RedirectService;
import org.example.service.ShortUrlService;
import org.example.session.UserSession;

public class LinkController {

    private final LinkService linkService;
    private final RedirectService redirectService;
    private final UserSession userSession;

    public LinkController(
            LinkService linkService, RedirectService redirectService, UserSession userSession) {
        this.linkService = linkService;
        this.redirectService = redirectService;
        this.userSession = userSession;
    }

    public String create(List<String> args) {
        if (args.isEmpty() || args.size() > 2) {
            return "Usage: create <url> [limit]";
        }

        String url = args.get(0);
        int limit = args.size() > 1 ? Integer.parseInt(args.get(1)) : AppConfig.DEFAULT_CLICK_LIMIT;

        UUID ownerId = userSession.getOrCreateUserId();
        ShortLink link = linkService.create(url, ownerId, limit);

        return "Created: " + ShortUrlService.toShortUrl(link.getCode());
    }

    public String open(List<String> args) {
        requireArgs(args, 1, "open <short-url>");

        try {
            if (!Desktop.isDesktopSupported()
                    || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                throw new IllegalStateException("Browser is not supported");
            }

            String code = ShortUrlService.extractCode(args.get(0));
            String originalUrl = redirectService.open(code);

            System.out.println(code);
            System.out.println(originalUrl);

            Desktop.getDesktop().browse(URI.create(originalUrl));

            return "Opening: " + originalUrl;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to open browser", e);
        }
    }

    public String list(List<String> args) {
        requireArgs(args, 0, "list");

        UUID ownerId = userSession.getUserId();

        if (ownerId == null) {
            return "You have no links yet";
        }

        List<ShortLink> links = linkService.listByOwner(ownerId);

        if (links.isEmpty()) {
            return "You have no links yet";
        }

        StringBuilder result = new StringBuilder();

        for (ShortLink link : links) {
            result.append(ShortUrlService.toShortUrl(link.getCode()))
                    .append(" -> ")
                    .append(link.getOriginalUrl())
                    .append(System.lineSeparator());
        }

        return result.toString().stripTrailing();
    }

    public String info(List<String> args) {
        requireArgs(args, 1, "info <short-url>");

        String code = ShortUrlService.extractCode(args.get(0));
        ShortLink link = linkService.getByCode(code);

        return """
                Short URL: %s
                Original URL: %s
                Clicks: %d / %d
                Created at: %s
                """
                .formatted(
                        ShortUrlService.toShortUrl(link.getCode()),
                        link.getOriginalUrl(),
                        link.getClickCount(),
                        link.getClickLimit(),
                        link.getCreatedAt())
                .stripTrailing();
    }

    public String edit(List<String> args) {
        requireArgs(args, 2, "edit <short-url> <new-limit>");

        String code = ShortUrlService.extractCode(args.get(0));
        int newLimit = parsePositiveInt(args.get(1));
        UUID ownerId = requireUserId();

        linkService.updateLimit(code, ownerId, newLimit);

        return "Click limit updated";
    }

    public String delete(List<String> args) {
        requireArgs(args, 1, "delete <short-url>");

        String code = ShortUrlService.extractCode(args.get(0));
        UUID ownerId = requireUserId();

        linkService.delete(code, ownerId);

        return "Link deleted";
    }

    private UUID requireUserId() {
        UUID userId = userSession.getUserId();

        if (userId == null) {
            throw new IllegalStateException("No active user session");
        }

        return userId;
    }

    private void requireArgs(List<String> args, int expected, String usage) {
        if (args.size() != expected) {
            throw new IllegalArgumentException("Usage: " + usage);
        }
    }

    private int parsePositiveInt(String value) {
        try {
            int number = Integer.parseInt(value);

            if (number <= 0) {
                throw new IllegalArgumentException("Limit must be a positive integer");
            }

            return number;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Limit must be a positive integer");
        }
    }
}
