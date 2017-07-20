package components.control;

import org.joml.Vector2f;

import components.Message;
import components.TypeComponent;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import entity.Entity;

public class LineControl extends BaseControl {

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Entity e) {
		BaseMovement movementComponent = getEntity().getComponent(TypeComponent.MOVEMENT);
		BaseGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);

		int direction;
		if (graphicsComponent instanceof AnimatedGraphics) {
			direction = ((AnimatedGraphics) graphicsComponent).getDirection();
		} else {
			direction = 0;
		}
		Vector2f movementVector = new Vector2f(0.0f, 0.0f);
		if ((direction >= 1) && (direction <= 3)) {
			movementVector.add(1.0f, 0.0f);
		}
		if (direction >= 5) {
			movementVector.add(-1.0f, 0.0f);
		}
		if ((direction <= 1) || (direction >= 7)) {
			movementVector.add(0.0f, -1.0f);
		}
		if ((direction >= 3) && (direction <= 5)) {
			movementVector.add(0.0f, 1.0f);
		}
		movementVector.normalize();
		movementComponent.move(e, movementVector);
	}

}
