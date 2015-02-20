package Object;

import Display.Image;

public class Enemy extends NPC {

	public Enemy(float x, float y, float w, float h, int speed, int direction,
			Image image) {
		super(x, y, w, h, speed, direction, image);
		width = 64;
		height = 64;
		team = 1;
	}
}
