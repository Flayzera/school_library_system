package src.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityLogger {
    private static final String LOG_FILE = "system_activity.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logActivity(String activity) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.println(String.format("[%s] %s", timestamp, activity));
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public static void logStudentActivity(String action, String studentName, String studentId, String... changes) {
        StringBuilder activity = new StringBuilder();
        activity.append(String.format("STUDENT %s - %s (ID: %s)", action, studentName, studentId));
        
        if (changes.length > 0) {
            activity.append("\nChanges made:");
            for (String change : changes) {
                activity.append("\n  - ").append(change);
            }
        }
        
        logActivity(activity.toString());
    }

    public static void logBookActivity(String action, String bookTitle, String bookId, String... changes) {
        StringBuilder activity = new StringBuilder();
        activity.append(String.format("BOOK %s - %s (ID: %s)", action, bookTitle, bookId));
        
        if (changes.length > 0) {
            activity.append("\nChanges made:");
            for (String change : changes) {
                activity.append("\n  - ").append(change);
            }
        }
        
        logActivity(activity.toString());
    }

    public static void logLoanActivity(String action, String studentName, String bookTitle, String dataEmprestimo, String dataDevolucao, String... changes) {
        StringBuilder activity = new StringBuilder();
        activity.append(String.format("LOAN %s - Student: %s, Book: %s", action, studentName, bookTitle));
        activity.append(String.format("\nData do empréstimo: %s", dataEmprestimo));
        activity.append(String.format("\nData de devolução: %s", dataDevolucao));
        
        if (changes.length > 0) {
            activity.append("\nChanges made:");
            for (String change : changes) {
                activity.append("\n  - ").append(change);
            }
        }
        
        logActivity(activity.toString());
    }
} 