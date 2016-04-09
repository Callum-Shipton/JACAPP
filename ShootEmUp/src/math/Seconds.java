package math;

import main.ShootEmUp;

public abstract class Seconds {

	public static int ticks(int seconds) {
		return (int) (seconds * ShootEmUp.getFPS());
	}
}
