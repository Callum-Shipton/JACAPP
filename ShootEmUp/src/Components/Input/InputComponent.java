package Components.Input;

import Math.Vector2;
import Object.Entity;

public interface InputComponent {
	
	void update(Entity e);
	void move(Entity e, Vector2 moveVec);
}
