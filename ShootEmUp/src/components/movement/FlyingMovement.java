package components.movement;

import object.Entity;
import math.Vector4;
import components.collision.BaseCollision;
import components.graphical.BaseGraphics;

public class FlyingMovement extends BasicMovement {
	
	public FlyingMovement(Entity e, BaseCollision BC, BaseGraphics BG, int speed) {
		super(e, BC, BG, speed);
	}

	public Vector4 doesCollide(Entity moving, Entity checked) {
		if(flat == true){
			return null;
		}
		return super.doesCollide(moving, checked);
	}
}