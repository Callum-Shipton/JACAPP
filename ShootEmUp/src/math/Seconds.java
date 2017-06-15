package math;

import main.Loop;

public abstract class Seconds {

	public static int ticks(int seconds) {
		return (int) (seconds * Loop.getFPS());
	}
}
