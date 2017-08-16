package components.control;

import org.joml.Vector2f;

import components.Message;
import components.TypeComponent;
import components.graphical.AnimatedGraphics;
import components.graphical.BaseGraphics;
import components.movement.BaseMovement;
import entity.Entity;
import input.Keyboard;
import input.Keys;
import loop.GameLoop;
import math.VectorMath;

public class PlayerControl extends BaseControl {

	public PlayerControl() {

	}

	public PlayerControl(PlayerControl playerControl) {

	}

	@Override
	public void receive(Message m) {

	}

	private void checkMovement(Entity e) {
		BaseMovement movementComponent = getEntity().getComponent(TypeComponent.MOVEMENT);
		AnimatedGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);

		Vector2f movement = new Vector2f(0.0f, 0.0f);
		if ((Keyboard.getKey(Keys.getKey("moveUp")) == 1) || (Keyboard.getKey(Keys.getKey("moveUp")) == 2)) {
			if(graphicsComponent.getY()> 0) movement.add(0.0f, -1.0f);
		}
		if ((Keyboard.getKey(Keys.getKey("moveDown")) == 1) || (Keyboard.getKey(Keys.getKey("moveDown")) == 2)) {
			if(graphicsComponent.getY()+graphicsComponent.getHeight()< GameLoop.getDisplay().getHeight()) movement.add(0.0f, 1.0f);
		}

		if (movement.length() > 0) {
			graphicsComponent.setAnimating(true);
			movementComponent.move(e, movement);
		} else {
			graphicsComponent.setAnimating(false);
		}
	}


	

	@Override
	public void update() {
		checkMovement(getEntity());
	}
}
