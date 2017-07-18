package components.spawn;

import entity.Entity;

@FunctionalInterface
public interface SpawnComponent {

	void spawn(Entity e);

}
