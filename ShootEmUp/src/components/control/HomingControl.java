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

	private BaseMovement movement;

	public HomingControl(AnimatedGraphics graphics, BaseMovement movement) {
		this.graphics = graphics;
		this.movement = movement;
	}

	@Override
	public void receive(Message m, Entity e) {
	}

	@Override
	public void update(Entity e) {
		Entity target = ShootEmUp.getGame().getPlayer();
		BaseGraphics playerGraphics = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.GRAPHICS);

		Vector2f targetVector = new Vector2f(playerGraphics.getX(), playerGraphics.getY());

		float y = graphics.getY();
		float x = graphics.getX();
		int speed = movement.getSpeed();

		if (target != null) {
			Vector2f movementVector = calculateMovementVector(targetVector, x, y, speed);
			if (movementVector.length() > 0) {
				if (graphics instanceof AnimatedGraphics) {
					((AnimatedGraphics) graphics).setAnimating(true);
				}
				movement.move(e, movementVector);
				if (graphics instanceof AnimatedGraphics) {
					((AnimatedGraphics) graphics)
							.setDirection((int) (Math.round(VectorMath.angle(movementVector)) / 45));
				}

			} else if (graphics instanceof AnimatedGraphics) {
				((AnimatedGraphics) graphics).setAnimating(false);
			}
		}
	}
}