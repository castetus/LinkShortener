package org.example;

import org.example.cli.ConsoleApp;
import org.example.controller.LinkController;
import org.example.service.LinkCleanupService;
import org.example.service.LinkService;
import org.example.service.RedirectService;
import org.example.session.UserSession;

public class Main {
    public static void main(String[] args) {
        LinkService linkService = new LinkService();
        RedirectService redirectService = new RedirectService(linkService);
        UserSession userSession = new UserSession();
        LinkController linkController =
                new LinkController(linkService, redirectService, userSession);
        ConsoleApp app = new ConsoleApp(linkController);

        LinkCleanupService cleanupService = new LinkCleanupService(linkService);

        cleanupService.start();

        try {
            app.run();
        } finally {
            cleanupService.stop();
        }
    }
}
