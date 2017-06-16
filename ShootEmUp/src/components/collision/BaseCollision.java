package components.collision;

import java.util.Set;

import components.Component;
import components.Message;
import components.TypeComponent;
import entity.Entity;
import main.ShootEmUp;
import math.Vector2;

public abstract class BaseCollision extends Component implements CollisionComponent {

	protected TypeComponent type = TypeComponent.COLLISION;

	protected Set<Vector2> gridPos;
	protected boolean moveBack;

	@Override
	public abstract void collision(Entity e, Entity hit);

	@Override
	public void destroy(Entity e) {
		ShootEmUp.getCurrentLevel().removeEntity(gridPos, e);
	}

	public Set<Vector2> getGridPos() {
		return gridPos;
	}

	public boolean getMoveBack() {
		return moveBack;
	}

	@Override
	public TypeComponent getType() {
		return type;
	}

	@Override
	public void receive(Message m, Entity e) {

	}

	public void setGridPos(Set<Vector2> gridPos) {
		this.gridPos = gridPos;
	}
}
