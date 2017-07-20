package components.collision;

import java.util.Set;

import org.joml.Vector2f;

import components.Component;
import components.Message;
import components.MessageId;
import components.TypeComponent;
import main.ShootEmUp;
import object.EntityMap;

public abstract class BaseCollision extends Component implements CollisionComponent {

	protected transient Set<Vector2f> gridPos;
	protected transient boolean moveBack;

	public BaseCollision() {
	}

	@Override
	public void destroy() {
		ShootEmUp.getGame().getCurrentLevel().removeEntity(gridPos, getEntity());
	}

	public Set<Vector2f> getGridPos() {
		return gridPos;
	}

	public boolean getMoveBack() {
		return moveBack;
	}

	@Override
	public TypeComponent getType() {
		return TypeComponent.COLLISION;
	}

	@Override
	public void receive(Message m) {
		if (m.getId() == MessageId.ENTITY_MOVED && ShootEmUp.getGame().getCurrentLevel() != null) {
			EntityMap eMap = ShootEmUp.getGame().getCurrentLevel().geteMap();
			if (getGridPos() != null) {
				eMap.removeEntity(getGridPos(), getEntity());
				setGridPos(eMap.getGridPos(getEntity()));
				eMap.addEntity(getGridPos(), getEntity());
			}
		}
	}

	public void setGridPos(Set<Vector2f> gridPos) {
		this.gridPos = gridPos;
	}
}
