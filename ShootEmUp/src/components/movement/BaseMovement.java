package components.movement;

import org.joml.Vector2f;

import components.Component;
import components.Message;
import components.TypeComponent;
import entity.Entity;
import loop.Loop;

public abstract class BaseMovement extends Component implements MovementComponent {

	protected int speed;
	protected int realSpeed;

	private boolean frost = false;
	private int frostCounter = 0;
	private int frostTime = 5;

	public BaseMovement(int speed) {
	

		this.speed = speed;
		realSpeed = speed;
	}

	@Override
	public void destroy() {

	}

	public int getSpeed() {
		return speed;
	}

	@Override
	public TypeComponent getType() {
		return TypeComponent.MOVEMENT;
	}

	public void increaseSpeed(int increase) {
		speed += increase;
	}

	public boolean isFrost() {
		return frost;
	}

	@Override
	public void move(Entity e, Vector2f moveVec) {
		if (frost) {
			speed = speed / 2;
			frostCounter++;
			if (frostCounter > Loop.ticks(frostTime)) {
				frost = false;
				speed = realSpeed;
				frostCounter = 0;
			}
		}
	}

	@Override
	public void receive(Message m) {
	}

	public void setFrost(boolean frost) {
		this.frost = frost;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public void update() {
	}
}
