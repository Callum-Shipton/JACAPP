package Components.Collision;

import Components.Message;
import Main.ShootEmUp;
import Object.Entity;

public class PickupCollision extends BaseCollision {

	public PickupCollision(Entity e){
		moveBack = false;
		setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
		ShootEmUp.currentLevel.eMap.addEntity(getGridPos(), e);
	}
	
	@Override
	public void collision(Entity hitter, Entity hit) {
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, hitter);
		ShootEmUp.currentLevel.oldEntities.add(hitter);
	}

	@Override
	public void update(Entity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub
		
	}

}
