package com.example.demo.api.errors;

import java.util.stream.Collectors;

public final class ApiErrorPrinter {
    private ApiErrorPrinter() {}

    public static String print(ApiError error) {
        if (error == null) {
            return "[0] Unknown error";
        }

        if (error.errors() == null || error.errors().isEmpty()) {
            return "[" + error.status() + "] " + error.message();
        }

        String details =
                error.errors().stream()
                        .map(item -> item.field() + ": " + item.message())
                        .collect(Collectors.joining("; "));

        return "[" + error.status() + "] " + error.message() + " -> " + details;
    }
}
