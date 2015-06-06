package Components.Spawn;

import Object.Entity;

public abstract class BaseSpawn implements SpawnComponent {
	
	@Override
	public abstract void spawn(Entity e);
	
}
