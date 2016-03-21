package components.spawn;

import object.Entity;
import components.Component;
import components.TypeComponent;

public abstract class BaseSpawn extends Component implements SpawnComponent {
	
	protected TypeComponent type = TypeComponent.SPAWN;
	
	@Override
	public abstract void spawn(Entity e);

	public void destroy(Entity e){
		
	}
	
	public TypeComponent getType() {
		return type;
	}
	
}
