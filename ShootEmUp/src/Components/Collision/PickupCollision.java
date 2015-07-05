package Components.Collision;

import Components.ComponentType;
import Components.Message;
import Components.Control.PlayerControl;
import Main.ShootEmUp;
import Object.Entity;

public class PickupCollision extends BaseCollision {

	public PickupCollision(Entity e){
		moveBack = false;
		setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
		ShootEmUp.currentLevel.eMap.addEntity(getGridPos(), e);
	}
	
	@Override
	public void collision(Entity e, Entity hit) {
		if(hit.getComponent(ComponentType.CONTROL) instanceof PlayerControl){
		ShootEmUp.currentLevel.eMap.removeEntity(gridPos, e);
		ShootEmUp.currentLevel.oldEntities.add(e);
		e.destroy();
		}
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
