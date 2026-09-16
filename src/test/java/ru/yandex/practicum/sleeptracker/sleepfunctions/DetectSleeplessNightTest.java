package ru.yandex.practicum.sleeptracker.sleepfunctions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.Collections;
import java.util.List;

public class DetectSleeplessNightTest {
    private DetectSleeplessNight function;

    @BeforeEach
    public void setUp() {
        function = new DetectSleeplessNight();
    }

    @Test
    public void shouldReturnCorrectSleeplessNightCount() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("01.10.25 23:15;02.10.25 07:30;GOOD"),
                SleepingSession.of("02.10.25 23:50;03.10.25 06:40;NORMAL"),
                SleepingSession.of("03.10.25 14:10;03.10.25 15:00;NORMAL"),
                SleepingSession.of("03.10.25 23:40;04.10.25 08:00;BAD"),
                SleepingSession.of("05.10.25 00:10;05.10.25 06:20;GOOD"),
                SleepingSession.of("05.10.25 13:30;05.10.25 14:15;NORMAL"),
                SleepingSession.of("06.10.25 22:30;07.10.25 05:50;GOOD")
        );
        Assertions.assertEquals(1, function.apply(sessions).getResult());
    }

    @Test
    public void shouldReturnZeroWhenOneNightInList() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("01.10.25 23:15;02.10.25 07:30;GOOD")
        );
        Assertions.assertEquals(0, function.apply(sessions).getResult());
    }

    @Test
    public void shouldShiftIntervalStartWhenFirstSessionIsAfterMidnight() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("05.10.25 00:10;05.10.25 06:20;GOOD"),
                SleepingSession.of("06.10.25 23:00;07.10.25 07:00;GOOD")
        );
        Assertions.assertEquals(1, function.apply(sessions).getResult());
    }

    @Test
    public void shouldCountTwoSessionsInOneNightAsOne() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("01.10.25 22:30;01.10.25 23:45;BAD"),
                SleepingSession.of("02.10.25 01:00;02.10.25 06:00;NORMAL"),
                SleepingSession.of("03.10.25 23:00;04.10.25 07:00;GOOD")
        );
        Assertions.assertEquals(1, function.apply(sessions).getResult());
    }

    @Test
    public void shouldCountCorrectlyWhenDifferentMonths() {
        List<SleepingSession> sessions = List.of(
                SleepingSession.of("30.01.25 00:10;31.01.25 07:30;GOOD"),
                SleepingSession.of("31.01.25 23:10;01.02.25 05:30;BAD"),
                SleepingSession.of("02.02.25 11:10;02.02.25 14:30;NORMAL"),
                SleepingSession.of("03.02.25 00:10;03.02.25 07:30;GOOD")
        );
        Assertions.assertEquals(1, function.apply(sessions).getResult());
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
