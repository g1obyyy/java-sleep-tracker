package ru.yandex.practicum.sleeptracker.sleepfunctions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepType;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.Collections;
import java.util.List;

public class GetSleepingTypeTest {
    private GetSleepingType function;

    @BeforeEach
    public void setUp() {
        function = new GetSleepingType();
    }

    @Test
    public void shouldReturnOwlWhenClearLeader() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("01.10.25 23:30;02.10.25 09:30;GOOD"), // Сова
                SleepingSession.of("02.10.25 23:45;03.10.25 10:00;NORMAL"), // Сова
                SleepingSession.of("03.10.25 21:00;04.10.25 06:30;GOOD")  // Жаворонок
        );
        Assertions.assertEquals(SleepType.OWL, function.apply(sessions).getResult());
    }

    @Test
    public void shouldReturnPigeonWhenTie() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("01.10.25 23:30;02.10.25 09:30;GOOD"), // Сова
                SleepingSession.of("02.10.25 21:00;03.10.25 06:30;GOOD")  // Жаворонок
        );
        Assertions.assertEquals(SleepType.PIGEON, function.apply(sessions).getResult());
    }

    @Test
    public void shouldReturnPigeonWhenNoNightSessions() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("01.10.25 14:00;01.10.25 15:30;NORMAL"),
                SleepingSession.of("02.10.25 13:15;02.10.25 14:00;GOOD")
        );
        Assertions.assertEquals(SleepType.PIGEON, function.apply(sessions).getResult());
    }

    @Test
    public void shouldReturnPigeonWhenOnlyPigeonNights() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("01.10.25 22:30;02.10.25 08:00;GOOD")
        );
        Assertions.assertEquals(SleepType.PIGEON, function.apply(sessions).getResult());
    }

    @Test
    public void shouldThrowIllegalStateExceptionWhenListIsEmpty() {
        List<SleepingSession> sessions = Collections.emptyList();
        Assertions.assertThrows(IllegalStateException.class, () -> function.apply(sessions));
    }

    @Test
    public void shouldThrowNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> function.apply(null));
    }
}