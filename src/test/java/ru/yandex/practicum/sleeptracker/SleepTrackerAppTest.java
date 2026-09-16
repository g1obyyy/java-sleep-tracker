package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.exceptions.GetStatisticsException;
import ru.yandex.practicum.sleeptracker.exceptions.LoadSessionsException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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

    @Test
    public void shouldReturnEmptyListWhenFileIsEmpty() throws Exception {
        Path tempEmptyFile = Files.createTempFile("empty_log", ".txt");

        List<?> sessions = app.loadSessions(tempEmptyFile.toString());
        Assertions.assertTrue(sessions.isEmpty());

        Files.delete(tempEmptyFile);
    }

    @Test
    public void shouldThrowLoadSessionsExceptionWhenDataIsBroken() throws Exception {
        Path tempBrokenFile = Files.createTempFile("broken_log", ".txt");
        Files.writeString(tempBrokenFile, "01.10.25 23:15;dsadftgythyujujhgtfrd;GOOD");

        Assertions.assertThrows(LoadSessionsException.class, () -> app.loadSessions(tempBrokenFile.toString()));

        Files.delete(tempBrokenFile);
    }
}