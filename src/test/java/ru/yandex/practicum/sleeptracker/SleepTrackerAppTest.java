package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.exceptions.GetStatisticsException;
import ru.yandex.practicum.sleeptracker.exceptions.LoadSessionsException;

public class SleepTrackerAppTest {
    private SleepTrackerApp app;

    @BeforeEach
    public void setUp() {
        app = new SleepTrackerApp();
    }

    @Test
    public void shouldThrowGetStatisticsException() {
        Assertions.assertThrows(GetStatisticsException.class, () -> app.printStatistics(null));
    }

    @Test
    public void shouldThrowNullPointerExceptionLoadSessions() {
        Assertions.assertThrows(NullPointerException.class, () -> app.loadSessions(null));
    }

    @Test
    public void shouldThrowLoadSessionsException() {
        Assertions.assertThrows(LoadSessionsException.class, () -> app.loadSessions("random_file"));
    }

    @Test
    public void shouldReturnCorrectSessionsCount() throws LoadSessionsException {
        int count = app.loadSessions("src/main/resources/sleep_log.txt").size();
        Assertions.assertEquals(13, count);
    }
}