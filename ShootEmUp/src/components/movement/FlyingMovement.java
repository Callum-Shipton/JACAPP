package components.movement;

import components.collision.BaseCollision;

public class FlyingMovement extends GroundMovement {

	public FlyingMovement(BaseCollision collisionComponent, int speed) {
		super(collisionComponent, speed);
	}
}
