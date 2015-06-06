package Components.Physics;

import Math.Vector4;
import Object.Entity;

public interface PhysicsComponent {
	
	Vector4 doesCollide(Entity e, float x, float y);
	Vector4 collideFunction(Entity e, float x, float y);
	void onCollide(Entity hit);
}
