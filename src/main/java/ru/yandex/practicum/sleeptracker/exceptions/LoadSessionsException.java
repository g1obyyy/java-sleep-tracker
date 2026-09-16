package ru.yandex.practicum.sleeptracker.exceptions;

public class LoadSessionsException extends Exception {
    public LoadSessionsException(String message, Throwable cause) {
        super(message,cause);
    }

    public LoadSessionsException(String message) {
        super(message);
    }
}
