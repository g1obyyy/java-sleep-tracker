package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.exceptions.GetStatisticsException;
import ru.yandex.practicum.sleeptracker.exceptions.LoadSessionsException;
import ru.yandex.practicum.sleeptracker.sleepfunctions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class SleepTrackerApp {
    private final List<Function<List<? extends SleepingSession>, ? extends SleepAnalysisResult<?>>> functions = List.of(
            new AllSleepSessions(),
            new MinSessionMinutes(),
            new MaxSessionMinutes(),
            new AverageSessionMinutes(),
            new BadSleepSessions()
    );

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Enter filename - sleep_logs");
            return;
        }

        SleepTrackerApp app = new SleepTrackerApp();
        try {
            app.run(args[0]);
        } catch (Exception e) {
            System.out.println("Critical error: " + e.getMessage() + ". Cause: " + e.getCause());
        }
    }

    private List<SleepingSession> loadSessions(final String filepath) throws LoadSessionsException {
        Objects.requireNonNull(filepath, "Filepath cannot be null");
        final List<SleepingSession> sessions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sessions.add(SleepingSession.of(line));
            }
        } catch (Exception e) {
            throw new LoadSessionsException("Reading sessions failure", e);
        }
        return sessions;
    }

    private void printStatistics(final List<SleepingSession> sessions) throws GetStatisticsException {
        try {
            functions.stream()
                    .map(function -> function.apply(sessions))
                    .forEach(System.out::println);
        } catch (Exception e) {
            throw new GetStatisticsException("Statistic failure", e);
        }
    }

    private void run(final String filename) throws LoadSessionsException, GetStatisticsException {
        List<SleepingSession> sessions = loadSessions(filename);
        if (sessions.isEmpty()) {
            System.out.println("Log file is empty, there is no any Sleeping sessions.");
            return;
        }
        printStatistics(sessions);
    }
}