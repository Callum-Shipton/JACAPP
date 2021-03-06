package components.movement;

import org.joml.Vector2f;

import components.Component;
import components.Message;
import components.TypeComponent;
import components.collision.BaseCollision;
import entity.Entity;
import loop.Loop;
import main.ShootEmUp;
import object.EntityMap;

public abstract class BaseMovement extends Component implements MovementComponent {

	protected TypeComponent type = TypeComponent.MOVEMENT;

	protected int speed;
	protected int realSpeed;
	protected BaseCollision BC;
	protected boolean flat;

	private boolean frost = false;
	private int frostCounter = 0;
	private int frostTime = 5;

	@Override
	public void destroy(Entity e) {

	}

	public boolean getFlat() {
		return this.flat;
	}

	public int getSpeed() {
		return this.speed;
	}

	@Override
	public TypeComponent getType() {
		return this.type;
	}

	public void increaseSpeed(int increase) {
		this.speed += increase;
	}

	public boolean isFrost() {
		return this.frost;
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
		if (m == Message.ENTITY_MOVED) {
			EntityMap eMap = ShootEmUp.getGame().getCurrentLevel().geteMap();
			eMap.removeEntity(BC.getGridPos(), e);
			BC.setGridPos(eMap.getGridPos(e));
			eMap.addEntity(BC.getGridPos(), e);
		}
	}

	public void setFlat(boolean flat) {
		this.flat = flat;
	}

	public void setFrost(boolean frost) {
		this.frost = frost;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
