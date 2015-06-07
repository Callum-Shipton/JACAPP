package Components.Control;

import Math.Vector2;
import Object.Entity;

public interface ControlComponent {
	
	void update(Entity e);
	void move(Entity e, Vector2 moveVec);
}
