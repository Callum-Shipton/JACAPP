package Components.Physics;

import Math.Vector2;
import Object.Entity;

public interface PhysicsComponent {

	void move(Entity e, Vector2 moveVec);
	
}
