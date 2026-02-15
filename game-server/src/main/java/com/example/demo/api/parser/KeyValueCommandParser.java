package com.example.demo.api.parser;

import com.example.demo.api.errors.CommandParseException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class KeyValueCommandParser implements CommandParser<ParsedCommand> {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z0-9_-]*$");

    @Override
    public ParsedCommand parse(String rawCommand) {
        if (rawCommand == null || rawCommand.isBlank()) {
            throw new CommandParseException("Command is blank", rawCommand);
        }

        String[] parts = rawCommand.trim().split("\\s+");
        String command = normalizeCommand(parts[0], rawCommand);
        Map<String, String> args = new LinkedHashMap<>();

        for (int i = 1; i < parts.length; i++) {
            String token = parts[i];
            int separatorIndex = token.indexOf('=');
            if (separatorIndex <= 0 || separatorIndex == token.length() - 1) {
                throw new CommandParseException("Invalid token: " + token, rawCommand);
            }

            String key = token.substring(0, separatorIndex);
            String value = token.substring(separatorIndex + 1);
            if (!NAME_PATTERN.matcher(key).matches()) {
                throw new CommandParseException("Invalid argument key: " + key, rawCommand);
            }
            if (args.putIfAbsent(key, value) != null) {
                throw new CommandParseException("Duplicated argument: " + key, rawCommand);
            }
        }

        return new ParsedCommand(command, args);
    }

    private String normalizeCommand(String rawCommandName, String rawCommand) {
        if (!NAME_PATTERN.matcher(rawCommandName).matches()) {
            throw new CommandParseException("Invalid command name: " + rawCommandName, rawCommand);
        }
        return rawCommandName.toUpperCase(Locale.ROOT);
    }
}
