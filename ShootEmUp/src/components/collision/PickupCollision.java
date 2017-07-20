package components.collision;

import components.Message;
import components.MessageId;
import components.TypeComponent;
import components.control.PlayerControl;
import components.inventory.BaseInventory;
import components.inventory.TypePickup;
import entity.Entity;
import loop.Loop;
import main.ShootEmUp;

public class PickupCollision extends BaseCollision {

	private final TypePickup typePickup;
	private final String name;
	private final int DESPAWN_TIME = 10;
	private int timer = 0;

	public PickupCollision(TypePickup type, String name) {

		this.name = name;

		typePickup = type;
		moveBack = false;
	}

	@Override
	public void collision(Entity e, Entity hit) {
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		if (hit.getComponent(TypeComponent.CONTROL) instanceof PlayerControl) {
			if ((BI).giveItem(typePickup, name)) {
				e.send(new Message(MessageId.PICKUP));
				ShootEmUp.getGame().getCurrentLevel().removeEntity(gridPos, e);
				e.destroy();
			}
		}
	}

	@Override
	public void update() {
		if (timer >= Loop.ticks(DESPAWN_TIME)) {
			getEntity().destroy();
		}
		timer++;
	}

}
