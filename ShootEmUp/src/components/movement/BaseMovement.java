package components.movement;

import components.Component;
import components.Message;
import components.TypeComponent;
import components.collision.BaseCollision;
import components.graphical.BaseGraphics;
import main.ShootEmUp;
import math.Seconds;
import math.Vector2;
import math.Vector4;
import object.Entity;
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
	public abstract Vector4 collideFunction(BaseGraphics BG, float x, float y);

	@Override
	public void destroy(Entity e) {

	}

	@Override
	public abstract Vector4 doesCollide(Entity moving, Entity checked);

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
	public void move(Entity e, Vector2 moveVec) {
		if (this.frost == true) {
			this.speed = this.speed / 2;
			this.frostCounter++;
			if (this.frostCounter > Seconds.ticks(this.frostTime)) {
				this.frost = false;
				this.speed = this.realSpeed;
				this.frostCounter = 0;
			}
		}
	}

	@Override
	public void receive(Message m, Entity e) {
		if (m == Message.ENTITY_MOVED) {
			EntityMap eMap = ShootEmUp.getCurrentLevel().geteMap();
			eMap.removeEntity(this.BC.getGridPos(), e);
			this.BC.setGridPos(eMap.getGridPos(e));
			eMap.addEntity(this.BC.getGridPos(), e);
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
