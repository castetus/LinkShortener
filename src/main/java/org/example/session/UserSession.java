package org.example.session;

import java.util.UUID;

public class UserSession {
    private UUID userId;

    public UUID getUserId() {
        return this.userId;
    }

    public UUID getOrCreateUserId() {
        if (userId == null) {
            userId = UUID.randomUUID();
        }
        return userId;
    }
}
