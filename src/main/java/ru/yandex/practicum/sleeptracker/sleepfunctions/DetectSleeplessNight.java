package ru.yandex.practicum.sleeptracker.sleepfunctions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class DetectSleeplessNight implements Function<List<? extends SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<? extends SleepingSession> sessions) {
        Objects.requireNonNull(sessions, "Sessions list must be initialized");

        LocalDate firstDate = sessions.stream()
                .map(SleepingSession::getLocalDateStart)
                .min(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalStateException("There is no sessions yet."));

        LocalDate lastDate = sessions.stream()
                .map(SleepingSession::getLocalDateEnd)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalStateException("There is no sessions yet."));

        long totalNights = ChronoUnit.DAYS.between(firstDate, lastDate);
        long sleepNights = sessions.stream()
                .filter(SleepingSession::isNightSession)
                .map(SleepingSession::getLocalDateStart)
                .distinct()
                .count();
        return SleepAnalysisResult.of("Sleepless nights count", Math.max(0, totalNights - sleepNights));
    }
}
