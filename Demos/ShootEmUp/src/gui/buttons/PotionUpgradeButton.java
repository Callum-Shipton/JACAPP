package gui.buttons;

import components.TypeComponent;
import components.inventory.BaseInventory;
import gui.ButtonAction;
import main.ShootEmUp;

public class PotionUpgradeButton implements ButtonAction {
	@Override
	public void click() {
		BaseInventory PI = (ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY));
		if (PI.getCoins() >= 5) {
			PI.addMaxPotions(5);
			PI.spendCoins(5);
		}
	}
}
