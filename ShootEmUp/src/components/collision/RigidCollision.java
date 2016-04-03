package components.collision;

import components.graphical.BaseGraphics;
import main.ShootEmUp;
import object.Entity;

public class RigidCollision extends BaseCollision {

	public RigidCollision(Entity e , BaseGraphics BG) {
		moveBack = true;
		setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e, BG));
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
