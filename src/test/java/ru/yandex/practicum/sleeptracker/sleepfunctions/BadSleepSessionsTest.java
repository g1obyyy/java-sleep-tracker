package ru.yandex.practicum.sleeptracker.sleepfunctions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.Collections;
import java.util.List;

public class BadSleepSessionsTest {
    private BadSleepSessions function;

    @BeforeEach
    public void setUp() {
        function = new BadSleepSessions();
    }

    @Test
    public void shouldReturnTotalBadSessionsCount() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("01.10.25 23:15;02.10.25 07:30;GOOD"),
                SleepingSession.of("02.10.25 23:50;03.10.25 06:40;BAD"),
                SleepingSession.of("03.10.25 23:40;04.10.25 08:00;BAD")
        );

        Assertions.assertEquals(2, function.apply(sessions).getResult());
    }

    @Test
    public void shouldReturnZeroTotalBadSessionsCount() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("01.10.25 23:15;02.10.25 07:30;GOOD"),
                SleepingSession.of("02.10.25 23:50;03.10.25 06:40;NORMAL"),
                SleepingSession.of("03.10.25 23:40;04.10.25 08:00;GOOD")
        );
        Assertions.assertEquals(0, function.apply(sessions).getResult());
    }

    @Test
    public void shouldThrowNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> function.apply(null));
    }

    @Test
    public void shouldThrowIllegalStateException() {
        List<SleepingSession> sessions = Collections.emptyList();
        Assertions.assertThrows(IllegalStateException.class, () -> function.apply(sessions));
    }
}
