package gui.menus;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.Art;
import display.Image;
import gui.VerticalLayout;
import gui.Counter;
import gui.CounterButton;
import gui.TypeButton;
import main.ShootEmUp;

public class UpgradesMenu extends PauseMenu {

	Counter coins;

	public UpgradesMenu(Image menuImage) {
		super(menuImage);
		VerticalLayout buttonList = new VerticalLayout(30, 30, Art.getImage("InventoryButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.INVENTORY_UPGRADE, Art.getImage("Coin"), 5, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.POTIONS_UPGRADE, Art.getImage("Coin"), 5, 1f));
		menuItems.add(buttonList);
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		coins = new Counter(30.0f, 103.0f, Art.getImage("Coin"), true,
				BI.getCoins(),
				1.0f);
	}

	@Override
	public void update() {
		super.update();
		BaseInventory BI = ShootEmUp.getPlayer().getComponent(TypeComponent.INVENTORY);
		coins.update(
				BI.getCoins());
	}

	@Override
	public void render() {
		super.render();
		coins.render(Art.stat);
	}
}
