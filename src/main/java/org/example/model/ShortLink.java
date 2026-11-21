package org.example.model;

import java.time.Instant;
import java.util.UUID;

public class ShortLink {
    private final String originalUrl;
    private final String code;
    private final UUID ownerId;
    private int clickLimit;
    private int clickCount = 0;
    private final Instant createdAt;

    public ShortLink(
            String originalUrl,
            String code,
            UUID ownerId,
            int clickLimit,
            Instant createdAt
    ) {
        this.originalUrl = originalUrl;
        this.code = code;
        this.ownerId = ownerId;
        this.clickLimit = clickLimit;
        this.createdAt = createdAt;
    }

    public String getOriginalUrl() {
        return this.originalUrl;
    }

    public UUID getOwnerId() {
        return this.ownerId;
    }

    public int getClickLimit() {
        return this.clickLimit;
    }

    public int getClickCount() {
        return this.clickCount;
    }

    public void incrementClickCount() {
        this.clickCount++;
    }

    public void setClickLimit(int limit) {
        this.clickLimit = limit;
    }
}