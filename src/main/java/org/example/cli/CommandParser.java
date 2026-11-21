package org.example.cli;

import java.util.Arrays;
import java.util.List;

public class CommandParser {

    public ParsedCommand parse(String input) {
        if (input == null || input.isBlank()) {
            return new ParsedCommand("", List.of());
        }

        String[] parts = input.trim().split("\\s+");

        return new ParsedCommand(
                parts[0].toLowerCase(),
                Arrays.asList(parts).subList(1, parts.length)
        );
    }

    public record ParsedCommand(String name, List<String> args) {}
}