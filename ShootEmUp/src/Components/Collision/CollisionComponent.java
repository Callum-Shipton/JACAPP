package Components.Collision;

import Object.Entity;

public interface CollisionComponent {
	public void collision(Entity hitter, Entity hit);
}
