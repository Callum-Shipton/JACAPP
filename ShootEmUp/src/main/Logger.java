package main;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Logger {

	private static boolean info = true;
	private static boolean debug = true;

	private static Set<Category> categories = new HashSet<>(Arrays.asList(Category.ALL));

	private Logger() {
	}

	public static void info(String message) {
		if (info) {
			System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG INFO: " + message); // NOSONAR
		}
	}

	public static void warn(String message) {
		System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG WARNING: " + message); // NOSONAR
	}

	public static void debug(String message, Category c) {
		if (debug && (categories.contains(c) || categories.contains(Category.ALL)))
			System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG DEBUG: " + message); // NOSONAR
	}

	public static void debug(double d, Category c) {
		debug(Double.toString(d), c);
	}

	public static void error(String message) {
		System.err.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG ERROR: " + message); // NOSONAR
	}

	public static void error(Exception e) {
		System.err.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG ERROR: " + e.getMessage()); // NOSONAR
		e.printStackTrace(); // NOSONAR
	}

	public enum Category {
		ALL, ENGINE_STATS, CONTROLLER, AI
	}

}
