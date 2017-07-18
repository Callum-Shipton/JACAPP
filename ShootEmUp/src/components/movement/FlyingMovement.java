package components.movement;

import components.collision.BaseCollision;
import components.graphical.BaseGraphics;

public class FlyingMovement extends GroundMovement {

	public FlyingMovement(BaseCollision collisionComponent, BaseGraphics graphicsComponent, int speed) {
		super(collisionComponent, graphicsComponent, speed);
	}
}
