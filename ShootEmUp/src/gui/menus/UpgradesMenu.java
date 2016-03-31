package gui.menus;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.Art;
import display.Image;
import gui.ButtonList;
import gui.Counter;
import gui.CounterButton;
import gui.TypeButton;
import main.ShootEmUp;

public class UpgradesMenu extends PauseMenu {

	Counter coins;

	public UpgradesMenu(Image menuImage) {
		super(menuImage);
		ButtonList buttonList = new ButtonList(30, 30, Art.getImage("InventoryButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.INVENTORY_UPGRADE, Art.getImage("Coin"), 5, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.POTIONS_UPGRADE, Art.getImage("Coin"), 5, 1f));
		menuItems.add(buttonList);
		coins = new Counter(30.0f, 103.0f, Art.getImage("Coin"), true,
				((BaseInventory) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getCoins(),
				1.0f);
	}

	@Override
	public void update() {
		super.update();
		coins.update(
				((BaseInventory) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY)).getCoins());
	}

	@Override
	public void render() {
		super.render();
		coins.render(Art.stat);
	}
}
