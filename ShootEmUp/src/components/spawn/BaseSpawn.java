package components.spawn;

import components.Component;
import components.TypeComponent;

public abstract class BaseSpawn extends Component implements SpawnComponent {

	public BaseSpawn() {
	}

	@Override
	public void destroy() {

	}

	@Override
	public TypeComponent getType() {
		return TypeComponent.SPAWN;
	}

	@Override
	public void update() {
	}
}
