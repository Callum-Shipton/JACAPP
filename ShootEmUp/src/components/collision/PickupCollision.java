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

	private final TypePickup typePickup;
	private final String name;
	private final int DESPAWN_TIME = 10;
	private int timer = 0;

	public PickupCollision(Entity e, TypePickup type, String name) {
		typePickup = type;
		this.name = name;

		moveBack = false;

		EntityMap eMap = ShootEmUp.getGame().getCurrentLevel().geteMap();
		setGridPos(eMap.getGridPos(e));
		eMap.addEntity(getGridPos(), e);
	}

	@Override
	public void collision(Entity e, Entity hit) {
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		if (hit.getComponent(TypeComponent.CONTROL) instanceof PlayerControl) {
			if ((BI).giveItem(typePickup, name)) {
				e.send(Message.PICKUP);
				ShootEmUp.getGame().getCurrentLevel().removeEntity(gridPos, e);
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
		if (timer >= Loop.ticks(DESPAWN_TIME)) {
			e.destroy();
		}
		timer++;
	}

}
