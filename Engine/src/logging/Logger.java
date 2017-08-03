package logging;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import io.FileManager;

public final class Logger {

	private static boolean info = true;
	private static boolean debug = true;

	private static final String PROPERTIES_LOCATION = "res/Properties/logger.properties";

	private static Set<Category> categories;

	public static void info(String message) {
		if (info) {
			System.out.println(new Timestamp(System.currentTimeMillis()).toString() + " LOG INFO: " + message); // NOSONAR
		}
	}

	public static void warn(String message) {
		System.out.println(new Timestamp(System.currentTimeMillis()).toString() + " LOG WARNING: " + message); // NOSONAR
	}

	public static void debug(String message, Category c) {
		if (categories == null) {
			initialiseCategories();
		}
		if (debug && (categories.contains(c) || categories.contains(Category.ALL)))
			System.out.println(new Timestamp(System.currentTimeMillis()).toString() + " LOG DEBUG: " + message); // NOSONAR
	}

	public static void error(String message) {
		System.err.println(new Timestamp(System.currentTimeMillis()).toString() + " LOG ERROR: " + message); // NOSONAR
	}

	public static void error(Exception e) {
		error(e.getMessage());
		e.printStackTrace(); // NOSONAR
	}

	private static void initialiseCategories() {
		Set<Category> categories = new HashSet<>();
		for (String category : FileManager.getProperties(PROPERTIES_LOCATION)) {
			categories.add(Category.valueOf(category));
		}
		Logger.categories = categories;
	}

	public enum Category {
		ALL, ENGINE_STATS, CONTROLLER, AI, AI_GOALBOUNDING, ENTITIES
	}

	public static void setDebug(boolean debug) {
		Logger.debug = debug;
	}

}
