package ru.yandex.practicum.sleeptracker.sleepfunctions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class AverageSessionMinutes implements Function<List<? extends SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<? extends SleepingSession> sessions) {
        Objects.requireNonNull(sessions, "Sessions list must be initialized");
        long average = (long) sessions.stream()
                .mapToLong(session -> session.getDuration().toMinutes())
                .average()
                .orElseThrow(() -> new IllegalStateException("There is no sessions yet."));
        return SleepAnalysisResult.of("Average sleeping session time (minutes)", average);    }
}
