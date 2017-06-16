package components.collision;

import components.Message;
import components.TypeComponent;
import components.control.PlayerControl;
import components.inventory.BaseInventory;
import components.inventory.TypePickup;
import entity.Entity;
import loop.Loop;
import main.ShootEmUp;
import object.EntityMap;

public class PickupCollision extends BaseCollision {

	private TypePickup typePickup;
	private String name;
	private final int DESPAWN_TIME = 10;
	private int timer = 0;

	public PickupCollision(Entity e, TypePickup type, String name) {
		this.typePickup = type;
		this.name = name;

		this.moveBack = false;

		EntityMap eMap = ShootEmUp.getCurrentLevel().geteMap();
		setGridPos(eMap.getGridPos(e));
		eMap.addEntity(getGridPos(), e);
	}

	@Override
	public void collision(Entity e, Entity hit) {
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		if (hit.getComponent(TypeComponent.CONTROL) instanceof PlayerControl) {
			if ((BI).giveItem(this.typePickup, this.name)) {
				ShootEmUp.getCurrentLevel().removeEntity(this.gridPos, e);
				e.destroy();
			}
		}
	}

	@Override
	public void receive(Message m, Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Entity e) {
		if (this.timer >= Loop.ticks(this.DESPAWN_TIME)) {
			e.destroy();
		}
		this.timer++;
	}

}
