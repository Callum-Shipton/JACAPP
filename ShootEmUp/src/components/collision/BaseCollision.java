package components.collision;

import java.util.Set;

import components.Component;
import components.Message;
import components.TypeComponent;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public abstract class BaseCollision extends Component implements CollisionComponent {

	protected TypeComponent type = TypeComponent.COLLISION;

	protected Set<Vector2> gridPos;
	protected boolean moveBack;

	@Override
	public abstract void collision(Entity e, Entity hit);

	@Override
	public void destroy(Entity e) {
		ShootEmUp.getCurrentLevel().removeEntity(this.gridPos, e);
	}

	public Set<Vector2> getGridPos() {
		return this.gridPos;
	}

	public boolean getMoveBack() {
		return this.moveBack;
	}

	@Override
	public TypeComponent getType() {
		return this.type;
	}

	@Override
	public void receive(Message m, Entity e) {

	}

	public void setGridPos(Set<Vector2> gridPos) {
		this.gridPos = gridPos;
	}
}
