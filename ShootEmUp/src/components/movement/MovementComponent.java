package components.movement;

import org.joml.Vector4f;

import components.graphical.BaseGraphics;
import entity.Entity;
import math.Vector2;

public interface MovementComponent {

	Vector4f collideFunction(BaseGraphics BG, float x, float y);

	Vector4f doesCollide(Entity moving, Entity checked);

	void move(Entity e, Vector2 moveVec);

}
