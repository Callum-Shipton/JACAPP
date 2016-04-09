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
import object.EntityMap;

public class PickupCollision extends BaseCollision {

	private TypePickup type;
	private String name;
	private final int DESPAWN_TIME = 10;
	private int timer = 0;

	public PickupCollision(Entity e, TypePickup type, SubType subtype, SubSubType subsubtype) {
		this.type = type;

		moveBack = false;
	}

	public PickupCollision(Entity e, TypePickup type, String name) {
		this.type = type;
		this.name = name;

		moveBack = false;
		
		EntityMap eMap = ShootEmUp.getCurrentLevel().geteMap();
		setGridPos(eMap.getGridPos(e));
		eMap.addEntity(getGridPos(), e);
	}

	public PickupCollision(Entity e, TypePickup type, String subtype, String subsubtype) {
		this.type = type;

		moveBack = false;
		EntityMap eMap = ShootEmUp.getCurrentLevel().geteMap();
		setGridPos(eMap.getGridPos(e));
		eMap.addEntity(getGridPos(), e);
	}

	@Override
	public void collision(Entity e, Entity hit) {
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (hit.getComponent(TypeComponent.CONTROL) instanceof PlayerControl) {
			if ((BI).giveItem(type, name)) {
				ShootEmUp.getCurrentLevel().removeEntity(gridPos, e);
				e.destroy();
			}
		}
	}

	@Override
	public void update(Entity e) {
		if (timer >= Seconds.ticks(DESPAWN_TIME)) {
			e.destroy();
		}
		timer++;
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

}
