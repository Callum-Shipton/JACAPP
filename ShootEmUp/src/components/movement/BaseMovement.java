package components.movement;

import org.joml.Vector2f;

import components.Component;
import components.Message;
import components.MessageId;
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

	private boolean frost = false;
	private int frostCounter = 0;
	private int frostTime = 5;

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
		if (m.getId() == MessageId.ENTITY_MOVED) {
			if (ShootEmUp.getGame().getCurrentLevel() != null) {
				EntityMap eMap = ShootEmUp.getGame().getCurrentLevel().geteMap();
				if (BC.getGridPos() != null) {
					eMap.removeEntity(BC.getGridPos(), e);
					BC.setGridPos(eMap.getGridPos(e));
					eMap.addEntity(BC.getGridPos(), e);
				}
			}
		}
	}

	public void setFrost(boolean frost) {
		this.frost = frost;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
