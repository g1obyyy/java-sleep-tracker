package ru.yandex.practicum.sleeptracker.sleepfunctions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepType;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetSleepingType implements Function<List<? extends SleepingSession>, SleepAnalysisResult<SleepType>> {
    @Override
    public SleepAnalysisResult<SleepType> apply(List<? extends SleepingSession> sessions) {
        Objects.requireNonNull(sessions, "Sessions list must be initialized");

        if (sessions.isEmpty()) {
            throw new IllegalStateException("There is no sessions yet.");
        }

        Map<SleepType, Long> typeCounts = sessions.stream()
                .filter(SleepingSession::isNightSession)
                .collect(Collectors.groupingBy(SleepingSession::getNightSleepType, Collectors.counting()));
        if (typeCounts.isEmpty()) {
            return SleepAnalysisResult.of("Dominant sleep type", SleepType.PIGEON);
        }

        long maxCount = Collections.max(typeCounts.values());
        List<SleepType> topTypes = typeCounts.entrySet().stream()
                .filter(type -> type.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .toList();

        SleepType dominantType;
        if (topTypes.size() > 1) {
            dominantType = SleepType.PIGEON;
        } else {
            dominantType = topTypes.getFirst();
        }
        return SleepAnalysisResult.of("Dominant sleep type", dominantType);
    }
}
