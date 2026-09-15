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
    private static final LocalTime NIGHT_START = LocalTime.MIDNIGHT;
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

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
                .filter(DetectSleeplessNight::isNightSession)
                .map(SleepingSession::getLocalDateStart)
                .distinct()
                .count();
        return SleepAnalysisResult.of("Sleepless nights count", Math.max(0, totalNights - sleepNights));
    }

    private static <T extends SleepingSession> boolean isNightSession(final T session) {
        Objects.requireNonNull(session);
        if (session.getLocalDateStart().isEqual(session.getLocalDateEnd())) {
            return !session.getLocalTimeStart().isBefore(NIGHT_START) && session.getLocalTimeStart().isBefore(NIGHT_END);
        }
        return true;
    }
}
