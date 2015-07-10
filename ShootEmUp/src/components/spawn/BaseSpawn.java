package components.spawn;

import object.Entity;
import components.Component;
import components.ComponentType;

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
