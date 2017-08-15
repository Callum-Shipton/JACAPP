package components.control;

import org.joml.Vector2f;

import components.Message;
import components.TypeComponent;
import components.attack.BaseAttack;
import components.graphical.AnimatedGraphics;
import components.movement.BaseMovement;
import gui.buttons.UpgradeManaRegenButton;
import math.VectorMath;

public class TestingControl extends BaseControl {

	private transient Vector2f movement = new Vector2f(0.0f, 0.0f);
	private transient Vector2f dir = new Vector2f(0.0f, 0.0f);
	private transient boolean toggle = false;

	public TestingControl() {
		movement.add(0.0f, -1.0f);
		movement.add(-1.0f, 0.0f);
		movement.normalize();
	}

	public TestingControl(TestingControl testingControl) {
		this();
	}

	@Override
	public void receive(Message m) {

	}

	@Override
	public void update() {

		AnimatedGraphics graphicsComponent = getEntity().getComponent(TypeComponent.GRAPHICS);
		BaseAttack attackComponent = getEntity().getComponent(TypeComponent.ATTACK);
		BaseMovement movementComponent = getEntity().getComponent(TypeComponent.MOVEMENT);

		movementComponent.move(getEntity(), movement);

		new UpgradeManaRegenButton().click();
		if (toggle)
			dir.set(1.0f, 0.0f);
		else
			dir.set(0.0f, 1.0f);
		dir.normalize();
		graphicsComponent.setDirection((int) (Math.round(VectorMath.angle(dir)) / 45));
		if (attackComponent.attack(getEntity(), graphicsComponent.getDirection()))
			toggle = !toggle;
	}

}