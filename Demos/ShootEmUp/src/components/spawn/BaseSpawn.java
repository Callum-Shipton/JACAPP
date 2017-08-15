package components.spawn;

import components.Component;
import components.TypeComponent;
import components.interfaces.SpawnComponent;

public abstract class BaseSpawn extends Component implements SpawnComponent {

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
