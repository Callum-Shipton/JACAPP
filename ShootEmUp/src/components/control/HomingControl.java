package components.control;

import components.Message;
import components.TypeComponent;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public class HomingControl extends BaseControl {

	private BaseMovement movement;
	private AnimatedGraphics graphics;

	private int counter = 0;

	public HomingControl(AnimatedGraphics graphics, BaseMovement movement) {
		this.graphics = graphics;
		this.movement = movement;
	}

	@Override
	public void receive(Message m, Entity e) {
	}

	@Override
	public void update(Entity e) {
		Entity target = ShootEmUp.getPlayer();
		BaseGraphics playerGraphics = ShootEmUp.getPlayer().getComponent(TypeComponent.GRAPHICS);

		float targetY = playerGraphics.getY();
		float targetX = playerGraphics.getX();
		float y = graphics.getY();
		float x = graphics.getX();
		int speed = movement.getSpeed();

		if (target != null) {
			Vector2 movementVector = new Vector2(0.0f, 0.0f);
			if (targetY < y) {
				if ((targetY - y) > -speed) {
					movementVector.add(0.0f, (1.0f / speed) * (targetY - y));
				} else {
					movementVector.add(0.0f, -1.0f);
				}
			}
			if (targetX < x) {
				if ((targetX - x) > -speed) {
					movementVector.add((1.0f / speed) * (targetX - x), 0.0f);
				} else {
					movementVector.add(-1.0f, 0.0f);
				}
			}
			if (targetY > y) {
				if ((targetY - y) < speed) {
					movementVector.add(0.0f, (1.0f / speed) * (targetY - y));
				} else {
					movementVector.add(0.0f, 1.0f);
				}
			}
			if (targetX > x) {
				if ((targetX - x) < speed) {
					movementVector.add((1.0f / speed) * (targetX - x), 0.0f);
				} else {
					movementVector.add(1.0f, 0.0f);
				}
			}
			if (movementVector.length() > 0) {
				if (movementVector.length() > 1) {
					movementVector.normalize();
				}
				graphics.setAnimating(true);
				movement.move(e, movementVector);
				graphics.setDirection((int) (Math.round(movementVector.Angle()) / 45));
			} else {
				graphics.setAnimating(false);
			}
		}
		counter++;
		if (counter == 30) {
			// weapon.attack(AG.getX(), AG.getY(), getDirection(), getTeam());
			counter = 0;
		}
	}
}