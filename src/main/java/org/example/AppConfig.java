package org.example;

import java.time.Duration;

public final class AppConfig {

    public static final Duration LINK_TTL = Duration.ofHours(24);
    public static final long CLEANUP_INTERVAL_MINUTES = 1;

    private AppConfig() {}
}
