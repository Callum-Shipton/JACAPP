package components.collision;

import entities.Entity;
import main.ShootEmUp;

public class RigidCollision extends BaseCollision {

	public RigidCollision() {
		
	}

	@Override
	public void collision(Entity e, Entity hit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub

	}
	
	public void setUp(Entity e){
		moveBack = true;
		setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
		ShootEmUp.currentLevel.eMap.addEntity(getGridPos(), e);
	}
}
