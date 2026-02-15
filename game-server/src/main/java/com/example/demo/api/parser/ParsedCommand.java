package com.example.demo.api.parser;

import java.util.Map;

public record ParsedCommand(String command, Map<String, String> args) {
    public ParsedCommand {
        args = Map.copyOf(args);
    }
}
