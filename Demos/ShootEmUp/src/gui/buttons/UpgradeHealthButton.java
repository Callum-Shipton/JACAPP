package gui.buttons;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.inventory.BaseInventory;
import gui.ButtonAction;
import main.ShootEmUp;

public class UpgradeHealthButton implements ButtonAction {
	@Override
	public void click() {
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			BaseAttack BA = (ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.ATTACK));
			BA.setMaxHealth(BA.getMaxHealth() + 1);
			BA.setHealth(BA.getHealth() + 1);
			BI.spendLevelPoints(1);
		}
	}
}
