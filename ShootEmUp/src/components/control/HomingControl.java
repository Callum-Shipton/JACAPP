package components.control;

import org.joml.Vector2f;

import components.Message;
import components.TypeComponent;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import entity.Entity;
import main.ShootEmUp;
import math.VectorMath;

public class HomingControl extends BaseControl {

	@Override
	public void receive(Message m, Entity e) {
	}

	@Override
	public void update(Entity e) {
		BaseMovement movementComponent = getEntity().getComponent(TypeComponent.MOVEMENT);
		BaseGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);

		Entity target = ShootEmUp.getGame().getPlayer();
		BaseGraphics playerGraphics = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.GRAPHICS);

		Vector2f targetVector = new Vector2f(playerGraphics.getX(), playerGraphics.getY());

		float y = graphicsComponent.getY();
		float x = graphicsComponent.getX();
		int speed = movementComponent.getSpeed();

		if (target != null) {
			Vector2f movementVector = calculateMovementVector(targetVector, x, y, speed);
			if (movementVector.length() > 0) {
				if (graphicsComponent instanceof AnimatedGraphics) {
					((AnimatedGraphics) graphicsComponent).setAnimating(true);
				}
				movementComponent.move(e, movementVector);
				if (graphicsComponent instanceof AnimatedGraphics) {
					((AnimatedGraphics) graphicsComponent)
							.setDirection((int) (Math.round(VectorMath.angle(movementVector)) / 45));
				}

			} else if (graphicsComponent instanceof AnimatedGraphics) {
				((AnimatedGraphics) graphicsComponent).setAnimating(false);
			}
		}
	}
}