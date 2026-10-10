package ru.gelman.messenger.exception;

public class ChatCreationException extends RuntimeException {
    public ChatCreationException(String message) {
        super(message);
    }
}
