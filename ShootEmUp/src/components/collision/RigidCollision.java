package components.collision;

import object.Entity;
import main.ShootEmUp;

public class RigidCollision extends BaseCollision {

	public RigidCollision(Entity e) {
		moveBack = true;
		setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
		ShootEmUp.currentLevel.eMap.addEntity(getGridPos(), e);
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
