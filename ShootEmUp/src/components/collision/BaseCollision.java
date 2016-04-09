package components.collision;

import java.util.HashSet;

import components.Component;
import components.Message;
import components.TypeComponent;
import main.ShootEmUp;
import math.Vector2;
import object.Entity;

public abstract class BaseCollision extends Component implements CollisionComponent {

	protected TypeComponent type = TypeComponent.COLLISION;

	protected HashSet<Vector2> gridPos;
	protected boolean moveBack;

	@Override
	public abstract void collision(Entity e, Entity hit);

	@Override
	public void destroy(Entity e) {
		ShootEmUp.getCurrentLevel().removeEntity(gridPos, e);
	}

	@Override
	public TypeComponent getType() {
		return type;
	}

	public HashSet<Vector2> getGridPos() {
		return gridPos;
	}

	public void setGridPos(HashSet<Vector2> gridPos) {
		this.gridPos = gridPos;
	}

	public boolean getMoveBack() {
		return moveBack;
	}

	@Override
	public void receive(Message m, Entity e) {

	}
}
