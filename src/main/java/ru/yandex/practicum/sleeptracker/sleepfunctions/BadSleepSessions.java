package ru.yandex.practicum.sleeptracker.sleepfunctions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class BadSleepSessions implements Function<List<? extends SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<? extends SleepingSession> sessions) {
        Objects.requireNonNull(sessions, "Sessions list must be initialized");
        if (sessions.isEmpty()) {
            throw new IllegalStateException("There is no sessions yet.");
        }

        long badSessions = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();
        return SleepAnalysisResult.of("Bad sleeping sessions count", badSessions);
    }
}
