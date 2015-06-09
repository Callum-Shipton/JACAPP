package Components.Spawn;

import Components.Component;
import Components.ComponentType;
import Object.Entity;

public abstract class BaseSpawn extends Component implements SpawnComponent {
	
	protected ComponentType type = ComponentType.SPAWN;
	
	@Override
	public abstract void spawn(Entity e);

	public void destroy(Entity e){
		
	}
	
	public ComponentType getType() {
		return type;
	}
	
}
