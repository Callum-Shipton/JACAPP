package components.collision;

import java.util.Set;

import org.joml.Vector2f;

import components.Component;
import components.Message;
import components.MessageId;
import components.TypeComponent;
import entity.Entity;
import main.ShootEmUp;
import object.EntityMap;

public abstract class BaseCollision extends Component implements CollisionComponent {

	protected TypeComponent type = TypeComponent.COLLISION;

	protected Set<Vector2f> gridPos;
	protected boolean moveBack;

	@Override
	public void destroy(Entity e) {
		ShootEmUp.getGame().getCurrentLevel().removeEntity(gridPos, e);
	}

	public Set<Vector2f> getGridPos() {
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
		if (m.getId() == MessageId.ENTITY_MOVED && ShootEmUp.getGame().getCurrentLevel() != null) {
			EntityMap eMap = ShootEmUp.getGame().getCurrentLevel().geteMap();
			if (getGridPos() != null) {
				eMap.removeEntity(getGridPos(), e);
				setGridPos(eMap.getGridPos(e));
				eMap.addEntity(getGridPos(), e);
			}
		}
	}

	public void setGridPos(Set<Vector2f> gridPos) {
		this.gridPos = gridPos;
	}
}
