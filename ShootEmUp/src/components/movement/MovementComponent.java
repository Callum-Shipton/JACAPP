package components.movement;

import components.graphical.BaseGraphics;
import entity.Entity;
import math.Vector2;
import math.Vector4;

public interface MovementComponent {

	Vector4 collideFunction(BaseGraphics BG, float x, float y);

	Vector4 doesCollide(Entity moving, Entity checked);

	void move(Entity e, Vector2 moveVec);

}
