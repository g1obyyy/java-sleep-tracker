package ru.yandex.practicum.sleeptracker.sleepfunctions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class DetectSleeplessNight implements Function<List<? extends SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<? extends SleepingSession> sessions) {
        Objects.requireNonNull(sessions, "Sessions list must be initialized");

        LocalDate firstDate = sessions.stream()
                .map(this::getNightDate)
                .min(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalStateException("There is no sessions yet."));

        LocalDate lastDate = sessions.stream()
                .map(this::getNightDate)
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalStateException("There is no sessions yet."));

        long totalNights = ChronoUnit.DAYS.between(firstDate, lastDate) + 1;
        long sleepNights = sessions.stream()
                .filter(SleepingSession::isNightSession)
                .map(this::getNightDate)
                .distinct()
                .count();
        return SleepAnalysisResult.of("Sleepless nights count", totalNights - sleepNights);
    }

    private LocalDate getNightDate(final SleepingSession session) {
        if (session.getLocalTimeStart().isBefore(LocalTime.NOON)) {
            return session.getLocalDateStart().minusDays(1);
        }
        return session.getLocalDateStart();
    }
}
