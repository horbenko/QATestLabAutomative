package utils.logging;

import org.testng.Reporter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static java.lang.String.format;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Custom reporting wrapper for TestNG.
 */
public class CustomReporter {

    private CustomReporter() {
    }

    /**
     * Logs action step that will be highlighted in test execution report.
     */
    public static void logAction(String message) {
        Reporter.log(format("[%-12s] ACTION: %s", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME), message));
    }

    /**
     * Logs simple step.
     */
    public static void log(String message) {
        Reporter.log(format("[%-12s] %s", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME), message));
        System.out.println(message);
    }

    public static void writeLog(String browserName) { // TODO To debug.
        File logsFile = new File(browserName + "_logs.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logsFile))) {
            bw.write(EventHandler.sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
