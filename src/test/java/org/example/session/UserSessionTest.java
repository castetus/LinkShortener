
package org.example.session;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionTest {

    @Test
    void shouldHaveNoUserIdInitially() {
        UserSession session = new UserSession();

        assertNull(session.getUserId());
    }

    @Test
    void shouldGenerateUserIdOnFirstRequest() {
        UserSession session = new UserSession();

        UUID userId = session.getOrCreateUserId();

        assertNotNull(userId);
        assertEquals(userId, session.getUserId());
    }

    @Test
    void shouldReturnSameUserIdOnSubsequentRequests() {
        UserSession session = new UserSession();

        UUID firstId = session.getOrCreateUserId();
        UUID secondId = session.getOrCreateUserId();

        assertEquals(firstId, secondId);
    }
}
