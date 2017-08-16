package gui;

import display.Image;

public class Counter extends Icon {

	public Counter(float x, float y, Image i, float scale) {
		super(x, y, i, false, scale);
	}

	public void add() {
		currFrame.add(1.0f, 0);
	}
}
