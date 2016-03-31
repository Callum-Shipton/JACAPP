package components.spawn;

import components.Component;
import components.TypeComponent;
import object.Entity;

public abstract class BaseSpawn extends Component implements SpawnComponent {

	protected TypeComponent type = TypeComponent.SPAWN;

	@Override
	public abstract void spawn(Entity e);

	@Override
	public void destroy(Entity e) {

	}

	@Override
	public TypeComponent getType() {
		return type;
	}

}
