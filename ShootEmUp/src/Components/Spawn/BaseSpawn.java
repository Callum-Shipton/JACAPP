package Components.Spawn;

import Components.Component;
import Components.ComponentType;
import Object.Entity;

public abstract class BaseSpawn extends Component implements SpawnComponent {
	
	private static final ComponentType type = ComponentType.SPAWN;
	
	@Override
	public abstract void spawn(Entity e);
	
}
