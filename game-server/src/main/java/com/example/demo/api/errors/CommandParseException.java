package com.example.demo.api.errors;

public class CommandParseException extends RuntimeException {
    private final String input;

    public CommandParseException(String message, String input) {
        super(message);
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
