package application.managers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * The LogManager class provides functionality for logging messages to a log
 * file.
 */
public class LogManager {

	private static final String LOG_FILE_PATH = "log.txt";

	/**
	 * 
	 * Retrieves the current date and time in the specified format.
	 * 
	 * @return the current date and time as a formatted string
	 */
	private static String getCurrentDateAndTime() {
		// Get the current date and time
		LocalDateTime currentDateTime = LocalDateTime.now();

		// Define the desired date and time format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Format the current date and time
		return currentDateTime.format(formatter);
	}

	/**
	 * 
	 * Logs the specified message to the log file.
	 * 
	 * @param message the message to log
	 */
	public static void log(String message) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {

			// Write the log entry to the file
			writer.write(String.format("[%s] %s", getCurrentDateAndTime(), message));

			// append new Line
			writer.write(System.lineSeparator());
		} catch (IOException e) {
			System.out.println("Error occurred while writing to log file: " + e.getMessage());
		}
	}
}
