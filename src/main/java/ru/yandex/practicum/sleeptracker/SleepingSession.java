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

    public final LocalDateTime getLocalDateTimeStart() { return start; }
    public final LocalDateTime getLocalDateTimeEnd() { return end; }
    public final SleepQuality getQuality() { return quality; }
    public final Duration getDuration() { return duration; }
    public final LocalTime getLocalTimeStart() { return start.toLocalTime(); }
    public final LocalTime getLocalTimeEnd() { return end.toLocalTime(); }
    public final LocalDate getLocalDateStart() { return start.toLocalDate(); }
    public final LocalDate getLocalDateEnd() { return end.toLocalDate(); }

    @Override
    public int compareTo(SleepingSession o) {
        return duration.compareTo(o.getDuration());
    }
}
