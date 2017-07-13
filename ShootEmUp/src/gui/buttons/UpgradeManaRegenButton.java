package gui.buttons;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.inventory.BaseInventory;
import gui.ButtonAction;
import main.ShootEmUp;

public class UpgradeManaRegenButton implements ButtonAction {
	@Override
	public void click() {
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			PlayerAttack PA = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.ATTACK);
			PA.setMaxManaRegen((int) Math.ceil(PA.getMaxManaRegen() / 2.0));
			BI.spendLevelPoints(1);
		}
	}
}