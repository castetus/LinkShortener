
package org.example.cli;

import java.util.List;
import java.util.Scanner;
import org.example.controller.LinkController;

public class ConsoleApp {

    private final Scanner scanner;
    private final CommandParser parser;
    private final LinkController linkController;

    public ConsoleApp(LinkController linkController) {
        this.scanner = new Scanner(System.in);
        this.parser = new CommandParser();
        this.linkController = linkController;
    }

    public void run() {
        System.out.println("URL Shortener");
        printHelp();

        while (true) {
            System.out.print("> ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String input = scanner.nextLine();
            CommandParser.ParsedCommand command = parser.parse(input);

            try {
                if (execute(command)) {
                    break;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Goodbye!");
    }

    private boolean execute(CommandParser.ParsedCommand command) {
        List<String> args = command.args();

        switch (command.name()) {
            case "" -> {
                // Ignore empty input
            }

            case "help" -> {
                if (!args.isEmpty()) {
                    System.out.println("Usage: help");
                } else {
                    printHelp();
                }
            }

            case "create" ->
                    System.out.println(linkController.create(args));

            case "open" ->
                    System.out.println(linkController.open(args));

            case "list" ->
                    System.out.println(linkController.list(args));

            case "info" ->
                    System.out.println(linkController.info(args));

            case "edit" ->
                    System.out.println(linkController.edit(args));

            case "delete" ->
                    System.out.println(linkController.delete(args));

            case "whoami" -> {
                // Will be connected to UserSession
                System.out.println("User session not connected yet");
            }

            case "exit" -> {
                if (!args.isEmpty()) {
                    System.out.println("Usage: exit");
                } else {
                    return true;
                }
            }

            default -> System.out.println(
                    "Unknown command: " + command.name()
                            + ". Type 'help' for available commands."
            );
        }

        return false;
    }

    private void printHelp() {
        System.out.println("""

                Available commands:
                  create <url> <limit>             Create short link
                  open <short-url>                Open original URL
                  list                            List your links
                  info <short-url>                Show link information
                  edit <short-url> <new-limit>    Change click limit
                  delete <short-url>              Delete link
                  whoami                          Show current user UUID
                  help                            Show this help
                  exit                            Exit application
                """);
    }
}
