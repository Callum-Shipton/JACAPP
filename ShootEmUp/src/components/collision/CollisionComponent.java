package components.collision;

import entity.Entity;

public interface CollisionComponent {

	public void collision(Entity e, Entity hit);

	public void destroy();
}
