package org.example;

import org.example.cli.ConsoleApp;
import org.example.controller.LinkController;
import org.example.service.LinkService;
import org.example.service.RedirectService;
import org.example.session.UserSession;

// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        LinkService linkService = new LinkService();
        RedirectService redirectService = new RedirectService();
        UserSession userSession = new UserSession();
        LinkController linkController = new LinkController(
                linkService,
                redirectService,
                userSession
        );
        ConsoleApp app = new ConsoleApp(linkController);
        app.run();
    }
}
