package components.collision;

import entities.Entity;

public interface CollisionComponent {
	
	public void collision(Entity e, Entity hit);
	public void destroy(Entity e);
}
