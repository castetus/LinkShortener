package org.example.model;

import org.example.AppConfig;

import java.time.Duration;
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

    public String getCode() {
        return this.code;
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

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void incrementClickCount() {
        if (clickCount >= clickLimit) {
            throw new IllegalStateException("Click limit exceeded");
        }

        this.clickCount++;
    }

    public void updateClickLimit(int newLimit) {
        if (newLimit < clickCount) {
            throw new IllegalArgumentException(
                    "New limit cannot be less than current click count"
            );
        }

        this.clickLimit = newLimit;
    }

    public boolean isExpired(Instant now) {
        Duration ttl = AppConfig.LINK_TTL;
        Instant expiresAt = createdAt.plus(ttl);
        return !now.isBefore(expiresAt);
    }
}