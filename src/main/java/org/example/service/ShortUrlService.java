package org.example.service;

import org.example.AppConfig;

public final class ShortUrlService {
    private static final String BASE_URL = AppConfig.BASE_URL;

    private ShortUrlService() {}

    public static String toShortUrl(String code) {
        return BASE_URL + code;
    }

    public static String extractCode(String shortUrl) {
        if (!shortUrl.startsWith(BASE_URL)) {
            throw new IllegalArgumentException("Invalid short URL");
        }

        String code = shortUrl.substring(BASE_URL.length());

        if (code.isEmpty() || code.contains("/")) {
            throw new IllegalArgumentException("Invalid short URL");
        }

        return code;
    }
}
