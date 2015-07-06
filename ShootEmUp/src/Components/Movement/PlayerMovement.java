package Components.Movement;

import Components.Collision.BaseCollision;
import Components.Graphical.BaseGraphics;
import Object.Entity;

public class PlayerMovement extends BasicMovement{

	public PlayerMovement(Entity e, BaseCollision BC, BaseGraphics BG, int speed) {
		super(e, BC, BG, speed);
	}
	
	public void increaseSpeed(int increase){
		speed += increase;
	}
}