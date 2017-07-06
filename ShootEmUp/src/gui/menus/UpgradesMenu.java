package gui.menus;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.Image;
import display.ImageProcessor;
import gui.Counter;
import gui.CounterButton;
import gui.TypeButton;
import gui.VerticalLayout;
import main.ShootEmUp;

public class UpgradesMenu extends PauseMenu {

	Counter coins;

	public UpgradesMenu(Image menuImage) {
		super(menuImage);
	}

	@Override
	public void render() {
		super.render();
		coins.render(ImageProcessor.stat);
	}

	@Override
	public void update() {
		super.update();
		BaseInventory baseInventory = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		coins.update(baseInventory.getCoins());
	}

	@Override
	public void resetMenu() {
		super.resetMenu();
		VerticalLayout buttonList = new VerticalLayout(x + 30, y + 30,
				ImageProcessor.getImage("InventoryButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.INVENTORY_UPGRADE,
				ImageProcessor.getImage("InventoryButton"), ImageProcessor.getImage("Coin"), 5, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.POTIONS_UPGRADE,
				ImageProcessor.getImage("PotionsButton"), ImageProcessor.getImage("Coin"), 5, 1f));
		menuItems.add(buttonList);
		BaseInventory baseInventory = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		coins = new Counter(x + 30.0f, y + 103.0f, ImageProcessor.getImage("Coin"), true, baseInventory.getCoins(),
				1.0f);
	}
}
