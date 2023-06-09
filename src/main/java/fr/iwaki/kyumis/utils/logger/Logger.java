package fr.iwaki.kyumis.utils.logger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public static void log(LogType type, String log, String stacktrace) {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = "[" + now.format(formatter) + "]: ";
        String line = null;

        if(type == null) {
            System.out.println(formattedTime + ConsoleColors.RED + "Log type can't be null. Here's the log anyways: " + log + ConsoleColors.RESET);
            return;
        }

        line = switch (type) {
            case OK ->
                    formattedTime + ConsoleColors.GREEN_BOLD + type + ": " + ConsoleColors.GREEN + log + ConsoleColors.RESET + (stacktrace != null ? " Stacktrace: " + stacktrace : "");
            case WARNING ->
                    formattedTime + ConsoleColors.YELLOW_BOLD + type + ": " + ConsoleColors.YELLOW + log + ConsoleColors.RESET + (stacktrace != null ? " Stacktrace: " + stacktrace : "");
            case ERROR ->
                    formattedTime + ConsoleColors.RED_BOLD + type + ": " + ConsoleColors.RED + log + ConsoleColors.RESET + (stacktrace != null ? " Stacktrace: " + stacktrace : "");
        };

        System.out.println(line);
    }

    public static void log(LogType type, String log) {
        log(type, log, null);
    }

    public static void logNoType(String log) {

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = "[" + now.format(formatter) + "]: ";
        System.out.println(formattedTime + ConsoleColors.PURPLE + log + ConsoleColors.RESET);
    }
}
