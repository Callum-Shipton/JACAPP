package main;

public class Logger {
	
	public static void info(String message){
		System.out.println("LOG INFO: "  + message);
	}
	
	public static void warn(String message){
		System.out.println("LOG WARNING: "  + message);
	}
	
	public static void debug(String message){
		System.out.println("LOG DEBUG: "  + message);
	}
	
	public static void error(String message){
		System.out.println("LOG ERROR: "  + message);
	}

	public static void debug(double d) {
		debug(Double.toString(d));
	}
}
