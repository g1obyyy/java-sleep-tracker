package ru.yandex.practicum.sleeptracker.sleepfunctions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class MaxSessionMinutes implements Function<List<? extends SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<? extends SleepingSession> sessions) {
        Objects.requireNonNull(sessions, "Sessions list must be initialized");
        SleepingSession session = sessions.stream()
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalStateException("There is no sessions yet."));
        return SleepAnalysisResult.of("Max sleeping session time (minutes)", session.getDuration().toMinutes());
    }
}
