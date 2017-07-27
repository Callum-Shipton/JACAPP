package components.movement;

import org.joml.Vector2f;

import component.interfaces.MovementComponent;
import components.Component;
import components.Message;
import components.TypeComponent;
import entity.Entity;
import loop.Loop;

public abstract class BaseMovement extends Component implements MovementComponent {

	protected transient int currentSpeed;
	protected int speed;

	private transient boolean frost = false;
	private transient int frostCounter = 0;
	private transient int frostTime = 5;

	public BaseMovement(int speed) {
		this.currentSpeed = speed;
		this.speed = speed;
	}

	@Override
	public void destroy() {

	}

	public int getSpeed() {
		return currentSpeed;
	}

	@Override
	public TypeComponent getType() {
		return TypeComponent.MOVEMENT;
	}

	public void increaseSpeed(int increase) {
		currentSpeed += increase;
	}

	public boolean isFrost() {
		return frost;
	}

	@Override
	public void move(Entity e, Vector2f moveVec) {
		if (frost) {
			currentSpeed = currentSpeed / 2;
			frostCounter++;
			if (frostCounter > Loop.ticks(frostTime)) {
				frost = false;
				currentSpeed = speed;
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
		this.currentSpeed = speed;
	}

	@Override
	public void update() {
	}
}
