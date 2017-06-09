package main;

import java.sql.Timestamp;

public final class Logger {
	
	public static void info(String message){
		System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG INFO: "  + message);
	}
	
	public static void warn(String message){
		System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG WARNING: "  + message);
	}
	
	public static void debug(String message){
		System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG DEBUG: "  + message);
	}
	
	public static void debug(double d) {
		debug(Double.toString(d));
	}
	
	public static void error(String message){
		System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG ERROR: "  + message);
	}

	public static void error(Exception e){
		System.out.println(new Timestamp(System.currentTimeMillis()).toString() + "LOG ERROR: "  + e.getMessage());
	}
	
}
