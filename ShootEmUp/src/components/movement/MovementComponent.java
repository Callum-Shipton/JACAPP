package components.movement;

import org.joml.Vector2f;
import org.joml.Vector4f;

import entity.Entity;

public interface MovementComponent {

	Vector4f doesCollide(Entity moving, Entity checked);

	void move(Entity e, Vector2f moveVec);

}
