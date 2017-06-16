package components.spawn;

import components.Component;
import components.TypeComponent;
import entity.Entity;

public abstract class BaseSpawn extends Component implements SpawnComponent {

	protected TypeComponent type = TypeComponent.SPAWN;

	@Override
	public void destroy(Entity e) {

	}

	@Override
	public TypeComponent getType() {
		return this.type;
	}

	@Override
	public abstract void spawn(Entity e);

}
