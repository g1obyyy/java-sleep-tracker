package ru.yandex.practicum.sleeptracker.exceptions;

public class GetStatisticsException extends Exception {
    public GetStatisticsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetStatisticsException(String message) {
        super(message);
    }
}
