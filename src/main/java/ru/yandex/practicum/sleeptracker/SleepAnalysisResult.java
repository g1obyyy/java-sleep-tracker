package ru.yandex.practicum.sleeptracker;

import java.util.Objects;

public class SleepAnalysisResult<T> {
    private final String output;
    private final T result;

    private SleepAnalysisResult(final String output, final T result) {
        this.output = output;
        this.result = result;
    }

    public static <T> SleepAnalysisResult<T> of(final String output, final T result) {
        Objects.requireNonNull(output, "Comment your result by output message");
        Objects.requireNonNull(result, "Result of the operation cannot be Null");
        return new SleepAnalysisResult<>(output, result);
    }

    @Override
    public String toString() {
        return output + ": " + result;
    }

    public final T getResult() {
        return result;
    }

    public final String getOutput() {
        return output;
    }
}
