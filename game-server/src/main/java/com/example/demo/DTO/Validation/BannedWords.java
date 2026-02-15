package com.example.demo.DTO.Validation;

import java.util.Set;

public final class BannedWords {
    private static final Set<String> BANNED_WORDS = Set.of("admin", "moderator", "system");

    private BannedWords() {}

    public static boolean containsBanned(String text) {
        if (text == null)
            return false;

        String lower = text.toLowerCase();
        return BANNED_WORDS.stream().anyMatch(lower::contains);
    }
}
