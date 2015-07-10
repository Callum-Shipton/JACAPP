package components.movement;

import math.Vector2;
import math.Vector4;
import components.graphical.BaseGraphics;
import entities.Entity;

public interface MovementComponent {

    void move(Entity e, Vector2 moveVec);
	Vector4 doesCollide(Entity moving, Entity checked);
	Vector4 collideFunction(BaseGraphics BG, float x, float y);

}

