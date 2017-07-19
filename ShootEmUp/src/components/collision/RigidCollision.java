package components.collision;

import entity.Entity;

public class RigidCollision extends BaseCollision {

	public RigidCollision(Entity entity) {
		super(entity);

		moveBack = true;
	}

	@Override
	public void collision(Entity e, Entity hit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}
}
