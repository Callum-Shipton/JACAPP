package gui.menus;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.Art;
import display.Image;
import gui.Counter;
import gui.CounterButton;
import gui.TypeButton;
import gui.VerticalLayout;
import main.Loop;

public class UpgradesMenu extends PauseMenu {

	Counter coins;

	public UpgradesMenu(Image menuImage) {
		super(menuImage);
		VerticalLayout buttonList = new VerticalLayout(30, 30, Art.getImage("InventoryButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.INVENTORY_UPGRADE, Art.getImage("InventoryButton"),
				Art.getImage("Coin"), 5, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.POTIONS_UPGRADE, Art.getImage("PotionsButton"),
				Art.getImage("Coin"), 5, 1f));
		this.menuItems.add(buttonList);
		BaseInventory baseInventory = Loop.getPlayer().getComponent(TypeComponent.INVENTORY);
		this.coins = new Counter(30.0f, 103.0f, Art.getImage("Coin"), true, baseInventory.getCoins(), 1.0f);
	}

	@Override
	public void render() {
		super.render();
		this.coins.render(Art.stat);
	}

	@Override
	public void update() {
		super.update();
		BaseInventory baseInventory = Loop.getPlayer().getComponent(TypeComponent.INVENTORY);
		this.coins.update(baseInventory.getCoins());
	}
}
