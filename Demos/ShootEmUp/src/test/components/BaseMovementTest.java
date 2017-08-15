package test.components;


import org.joml.Vector2f;

import components.Component;
import components.Message;
import components.TypeComponent;
import components.interfaces.MovementComponent;
import entity.Entity;
import loop.GameLoop;



public abstract class BaseMovementTest extends Component implements MovementComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected transient static final TypeComponent type = TypeComponent.MOVEMENT;

	protected int speed;
	protected transient int realSpeed;

	protected transient boolean frost = false;
	protected transient int frostCounter = 0;
	protected transient int frostTime = 5;
	
	
	public BaseMovementTest(int speed) {
		this.speed = speed;
		realSpeed = speed;
	}
	public BaseMovementTest(BaseMovementTest bm) {
		this(bm.speed);
	}

	@Override
	public void destroy() {

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
			if (frostCounter > GameLoop.ticks(frostTime)) {
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
