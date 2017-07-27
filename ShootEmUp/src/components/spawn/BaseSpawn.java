package components.spawn;

import component.interfaces.SpawnComponent;
import components.Component;
import components.TypeComponent;

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
