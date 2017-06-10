package main;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Logger {

	private static boolean info = true;
	private static boolean debug = false;

	private static Set<Category> categories = new HashSet<>(Arrays.asList());

	private Logger() {
	}

	public static void info(String message) {
		if (info) {
			System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG INFO: " + message);
		}
	}

	public static void warn(String message) {
		System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG WARNING: " + message);
	}

	public static void debug(String message, Category c) {
		if (debug) {
			if (categories.contains(c) || categories.contains(Category.ALL))
				System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG DEBUG: " + message);
		}
	}

	public static void debug(double d, Category c) {
		debug(Double.toString(d), c);
	}

	public static void error(String message) {
		System.err.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG ERROR: " + message);
	}

	public static void error(Exception e) {
		System.err.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG ERROR: " + e.getMessage());
		e.printStackTrace();
	}

	public enum Category {
		ALL, ENGINE_STATS
	}

}
