package org.example.service;

import org.example.AppConfig;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LinkCleanupService {

    private final LinkService linkService;

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    public LinkCleanupService(LinkService linkService) {
        this.linkService = linkService;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(
                () -> linkService.removeExpired(Instant.now()),
                0,
                AppConfig.CLEANUP_INTERVAL_MINUTES,
                TimeUnit.MINUTES
        );
    }

    public void stop() {
        scheduler.shutdown();
    }
}