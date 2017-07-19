package components.spawn;

import components.Component;
import components.TypeComponent;
import entity.Entity;

public abstract class BaseSpawn extends Component implements SpawnComponent {

	public BaseSpawn(Entity entity) {
		super(entity);
	}

	@Override
	public void destroy(Entity e) {

	}

	@Override
	public TypeComponent getType() {
		return TypeComponent.SPAWN;
	}

	@Override
	public void update(Entity e) {
	}
}
