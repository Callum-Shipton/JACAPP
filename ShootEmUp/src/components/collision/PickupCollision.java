package components.collision;

import object.Entity;
import main.ShootEmUp;
import components.TypeComponent;
import components.Message;
import components.control.PlayerControl;
import components.inventory.PlayerInventory;
import components.inventory.Subtype;
import components.inventory.TypePickup;

public class PickupCollision extends BaseCollision {

	TypePickup type;
	Subtype subtype;
	
	public PickupCollision(Entity e, TypePickup type, Subtype subtype){
		this.type = type;
		this.subtype = subtype;
		
		moveBack = false;
		setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
		ShootEmUp.currentLevel.eMap.addEntity(getGridPos(), e);
	}
	
	@Override
	public void collision(Entity e, Entity hit) {
		if(hit.getComponent(TypeComponent.CONTROL) instanceof PlayerControl){
			((PlayerInventory)ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).giveItem(type, subtype);
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
