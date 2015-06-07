package Components.Movement;

import Math.Vector4;
import Object.Entity;

public interface MovementComponent {
	
	Vector4 doesCollide(Entity e, float x, float y);
	Vector4 collideFunction(Entity e, float x, float y);
}

