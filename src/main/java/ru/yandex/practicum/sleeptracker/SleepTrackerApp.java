package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.sleepfunctions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class SleepTrackerApp {
    private final List<Function<List<? extends SleepingSession>, ?>> functions = List.of(
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
            System.out.println("Critical error: " + e.getMessage());
        }
    }

    private List<SleepingSession> loadSessions(final String filepath) throws Exception {
        Objects.requireNonNull(filepath);
        final List<SleepingSession> sessions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sessions.add(SleepingSession.of(line));
            }
        }
        return sessions;
    }

    private void printStatistics(final List<SleepingSession> sessions) {
        functions.stream()
                .map(function -> function.apply(sessions))
                .forEach(System.out::println);
    }

    private void run(final String filename) throws Exception {
        List<SleepingSession> sessions = loadSessions(filename);
        if (sessions.isEmpty()) {
            System.out.println("Log file is empty, there is no any Sleeping sessions.");
            return;
        }
        printStatistics(sessions);
    }
}