package com.javarush.khmelov.config;

public class StoryParseException extends RuntimeException {
    public StoryParseException(String message) { super(message); }
    public StoryParseException(String message, Throwable cause) { super(message, cause); }
}
