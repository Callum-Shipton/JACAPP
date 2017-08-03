package gui.menus;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.Image;
import display.ImageProcessor;
import gui.Counter;
import gui.Button;
import gui.buttons.InventoryUpgradeButton;
import gui.buttons.PotionUpgradeButton;
import gui.layouts.HorizontalLayout;
import gui.layouts.VerticalLayout;
import main.ShootEmUp;

public class UpgradesMenu extends PauseMenu {

	Counter coinCounter;

	public UpgradesMenu(Image menuImage) {
		super(menuImage);
	}

	@Override
	public void update() {
		super.update();
		BaseInventory baseInventory = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		coinCounter.update(baseInventory.getCoins());
	}

	@Override
	public void resetMenu() {
		super.resetMenu();

		VerticalLayout buttonList = new VerticalLayout(x + 30, y + 30, 20);

		HorizontalLayout inventoryUpgrade = new HorizontalLayout(0);
		buttonList.addMenuItem(inventoryUpgrade);
		Button inventoryButton = new Button(ImageProcessor.getImage("InventoryButton"),
				new InventoryUpgradeButton());
		Counter inventoryPrice = new Counter(ImageProcessor.getImage("Coin"), 5);
		inventoryUpgrade.additem(inventoryButton);
		inventoryUpgrade.additem(inventoryPrice);

		HorizontalLayout potionsUpgrade = new HorizontalLayout(0);
		buttonList.addMenuItem(potionsUpgrade);
		Button potionsButton = new Button(ImageProcessor.getImage("PotionsButton"), new PotionUpgradeButton());
		Counter potionsPrice = new Counter(ImageProcessor.getImage("Coin"), 5);
		potionsUpgrade.additem(potionsButton);
		potionsUpgrade.additem(potionsPrice);

		addMenuItem(buttonList);

		BaseInventory baseInventory = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		coinCounter = new Counter(x + 30.0f, y + 103.0f, ImageProcessor.getImage("Coin"), true,
				baseInventory.getCoins(), 1.0f);

		addMenuItem(coinCounter);
	}
}
