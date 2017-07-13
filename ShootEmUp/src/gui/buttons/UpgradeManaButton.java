package gui.buttons;

import components.TypeComponent;
import components.attack.PlayerAttack;
import components.inventory.BaseInventory;
import gui.ButtonAction;
import main.ShootEmUp;

public class UpgradeManaButton implements ButtonAction {
	@Override
	public void click() {
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		if (BI.getLevelPoints() > 0) {
			PlayerAttack PA = (ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.ATTACK));
			PA.setMaxMana(PA.getMaxMana() + 1);
			PA.setMana(PA.getMana() + 1);
			BI.spendLevelPoints(1);
		}
	}
}
