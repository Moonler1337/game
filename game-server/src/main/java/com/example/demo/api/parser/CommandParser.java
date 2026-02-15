package com.example.demo.api.parser;

public interface CommandParser<T> {
    T parse(String rawCommand);
}
