package components.collision;

import components.Message;
import components.TypeComponent;
import components.control.PlayerControl;
import components.inventory.BaseInventory;
import components.inventory.SubSubType;
import components.inventory.SubType;
import components.inventory.TypePickup;
import main.ShootEmUp;
import math.Seconds;
import object.Entity;

public class PickupCollision extends BaseCollision {

	private TypePickup type;
	private SubType subtype;
	private SubSubType subsubtype;
	private final int DESPAWN_TIME = 10;
	private int timer = 0;

	public PickupCollision(Entity e, TypePickup type, SubType subtype, SubSubType subsubtype) {
		this.type = type;
		this.subtype = subtype;
		this.subsubtype = subsubtype;

		moveBack = false;
		setGridPos(ShootEmUp.currentLevel.eMap.getGridPos(e));
		ShootEmUp.currentLevel.eMap.addEntity(getGridPos(), e);
	}

	@Override
	public void collision(Entity e, Entity hit) {
		if (hit.getComponent(TypeComponent.CONTROL) instanceof PlayerControl) {
			if (((BaseInventory) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))
					.giveItem(type, subtype, subsubtype)) {
				ShootEmUp.currentLevel.eMap.removeEntity(gridPos, e);
				ShootEmUp.currentLevel.oldEntities.add(e);
				e.destroy();
			}
		}
	}

	@Override
	public void update(Entity e) {
		if(timer >= Seconds.ticks(DESPAWN_TIME)){
			e.destroy();
		}
		timer++;
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

}
