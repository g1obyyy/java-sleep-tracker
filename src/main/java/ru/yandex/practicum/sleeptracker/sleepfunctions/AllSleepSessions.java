package ru.yandex.practicum.sleeptracker.sleepfunctions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class AllSleepSessions implements Function<List<? extends SleepingSession>, SleepAnalysisResult<Integer>> {
    @Override
    public SleepAnalysisResult<Integer> apply(List<? extends SleepingSession> sessions) {
        Objects.requireNonNull(sessions, "Sessions list must be initialized");
        if (sessions.isEmpty()) {
            throw new IllegalStateException("There is no sessions yet.");
        }
        return SleepAnalysisResult.of("All sleeping sessions count", sessions.size());
    }
}
