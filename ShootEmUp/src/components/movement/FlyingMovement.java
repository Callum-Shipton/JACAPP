package components.movement;

import components.collision.BaseCollision;
import components.graphical.BaseGraphics;
import math.Vector4;
import object.Entity;

public class FlyingMovement extends BasicMovement {

	public FlyingMovement(BaseCollision BC, BaseGraphics BG, int speed) {
		super(BC, BG, speed);
	}

	@Override
	public Vector4 doesCollide(Entity moving, Entity checked) {
		if (this.flat) {
			return null;
		}
		return super.doesCollide(moving, checked);
	}
}
