package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Properties;

public final class AppConfig {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = Files.newInputStream(Path.of("config.properties"))) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load config.properties", e);
        }
    }

    public static final Duration LINK_TTL =
            Duration.ofHours(Long.parseLong(PROPERTIES.getProperty("link.ttl.hours")));

    public static final long CLEANUP_INTERVAL_MINUTES =
            Long.parseLong(PROPERTIES.getProperty("link.cleanup.interval.minutes"));

    public static final int SHORT_CODE_LENGTH =
            Integer.parseInt(PROPERTIES.getProperty("link.code.length"));

    public static final int DEFAULT_CLICK_LIMIT =
            Integer.parseInt(PROPERTIES.getProperty("link.default-click-limit"));

    public static final String BASE_URL = PROPERTIES.getProperty("link.base-url");

    private AppConfig() {}
}
