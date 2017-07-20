package test.components;

import java.io.Serializable;

import org.joml.Vector2f;

import components.Component;
import components.Message;
import components.TypeComponent;
import components.movement.MovementComponent;
import entity.Entity;
import loop.Loop;

public abstract class BaseMovementTest extends Component implements MovementComponent, Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final TypeComponent type = TypeComponent.MOVEMENT;

	protected int speed;
	protected int realSpeed;

	protected boolean frost = false;
	protected int frostCounter = 0;
	protected int frostTime = 5;

	public BaseMovementTest(int speed) {
		this.speed = speed;
		realSpeed = speed;
	}
	
	public BaseMovementTest(BaseMovementTest bm) {
		this(bm.speed);
		realSpeed = speed;
	}
	
	@Override
	public BaseMovementTest clone() throws CloneNotSupportedException {
			BaseMovementTest result = (BaseMovementTest) super.clone();
			return result;	
	}

	@Override
	public void destroy(Entity e) {

	}

	public int getSpeed() {
		return speed;
	}

	@Override
	public TypeComponent getType() {
		return type;
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
	public void receive(Message m, Entity e) {
	}

	public void setFrost(boolean frost) {
		this.frost = frost;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public void update(Entity e) {
	}
}
