package Components.Movement;

import Math.Vector2;
import Object.Entity;

public abstract class BaseMovement implements MovementComponent {
	public abstract void move(Entity e, Vector2 moveVec);
}
