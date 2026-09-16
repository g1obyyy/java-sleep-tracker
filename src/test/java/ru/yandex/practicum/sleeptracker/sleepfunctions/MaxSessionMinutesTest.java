package ru.yandex.practicum.sleeptracker.sleepfunctions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.Collections;
import java.util.List;

public class MaxSessionMinutesTest {
    private MaxSessionMinutes function;

    @BeforeEach
    public void setUp() {
        function = new MaxSessionMinutes();
    }

    @Test
    public void shouldReturnMaxMinutesSession() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("01.10.25 23:00;02.10.25 07:00;GOOD"),
                SleepingSession.of("02.10.25 23:00;03.10.25 06:00;NORMAL"),
                SleepingSession.of("03.10.25 23:00;04.10.25 08:00;BAD")
        );
        Assertions.assertEquals(540, function.apply(sessions).getResult());
    }

    @Test
    public void shouldThrowIllegalStateException() {
        List<SleepingSession> sessions = Collections.emptyList();
        Assertions.assertThrows(IllegalStateException.class, () -> function.apply(sessions));
    }

    @Test
    public void shouldThrowNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> function.apply(null));
    }
}
