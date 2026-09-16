package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SleepingSession implements Comparable<SleepingSession> {
    private static final String DELIMITER = ";";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private static final LocalTime NIGHT_START = LocalTime.MIDNIGHT;
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    private static final LocalTime OWL_START = LocalTime.of(23, 0);
    private static final LocalTime OWL_END = LocalTime.of(9, 0);
    private static final LocalTime LARK_START = LocalTime.of(22, 0);
    private static final LocalTime LARK_END = LocalTime.of(7, 0);

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final SleepQuality quality;
    private final Duration duration;

    private SleepingSession(final LocalDateTime start, final LocalDateTime end, final SleepQuality quality, final Duration duration) {
        this.start = start;
        this.end = end;
        this.quality = quality;
        this.duration = duration;
    }

    public static SleepingSession of(String line) {
        Objects.requireNonNull(line, "Sleeping session's info cannot be null");
        String[] tokens = line.split(DELIMITER);

        LocalDateTime start = LocalDateTime.parse(tokens[0], formatter);
        LocalDateTime end = LocalDateTime.parse(tokens[1], formatter);
        SleepQuality quality = SleepQuality.valueOf(tokens[2]);
        Duration duration = Duration.between(start, end);

        return new SleepingSession(start, end, quality, duration);
    }

    public final LocalDateTime getLocalDateTimeStart() {
        return start;
    }

    public final LocalDateTime getLocalDateTimeEnd() {
        return end;
    }

    public final SleepQuality getQuality() {
        return quality;
    }

    public final Duration getDuration() {
        return duration;
    }

    public final LocalTime getLocalTimeStart() {
        return start.toLocalTime();
    }

    public final LocalTime getLocalTimeEnd() {
        return end.toLocalTime();
    }

    public final LocalDate getLocalDateStart() {
        return start.toLocalDate();
    }

    public final LocalDate getLocalDateEnd() {
        return end.toLocalDate();
    }


    public boolean isNightSession() {
        if (getLocalDateStart().isEqual(getLocalDateEnd())) {
            return !getLocalTimeStart().isBefore(NIGHT_START) && getLocalTimeStart().isBefore(NIGHT_END);
        }
        return true;
    }

    public SleepType getNightSleepType() {
        if (isOwl()) {
            return SleepType.OWL;
        }
        if (isLark()) {
            return SleepType.LARK;
        }
        return SleepType.PIGEON;
    }

    private boolean isOwl() {
        final LocalTime sleepStart = getLocalTimeStart();
        final LocalTime sleepEnd = getLocalTimeEnd();

        boolean fallsAsleep = sleepStart.isAfter(OWL_START) || sleepStart.isBefore(OWL_END);
        boolean wakesUp = sleepEnd.isAfter(OWL_END);
        return fallsAsleep && wakesUp;
    }

    private boolean isLark() {
        final LocalTime sleepStart = getLocalTimeStart();
        final LocalTime sleepEnd = getLocalTimeEnd();

        boolean fallsAsleep = sleepStart.isBefore(LARK_START) && sleepStart.isAfter(LARK_END);
        boolean wakesUp = sleepEnd.isBefore(LARK_END);
        return fallsAsleep && wakesUp;
    }
}
