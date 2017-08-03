package gui.menus;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.Image;
import display.ImageProcessor;
import gui.Counter;
import gui.MenuButton;
import gui.buttons.UpgradeHealthButton;
import gui.buttons.UpgradeHealthRegenButton;
import gui.buttons.UpgradeManaButton;
import gui.buttons.UpgradeManaRegenButton;
import gui.layouts.HorizontalLayout;
import gui.layouts.VerticalLayout;
import main.ShootEmUp;

public class SkillMenu extends PauseMenu {

	Counter skillPoints;

	public SkillMenu(Image menuImage) {
		super(menuImage);
	}

	@Override
	public void render() {
		super.render();
		skillPoints.render(ImageProcessor.stat);
	}

	@Override
	public void update() {
		super.update();
		BaseInventory playerInventory = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		skillPoints.update(playerInventory.getLevelPoints());
	}

	@Override
	public void resetMenu() {
		super.resetMenu();
		VerticalLayout buttonList = new VerticalLayout(x + 30, y + 30, 20);
		HorizontalLayout healthRegenUpgrade = new HorizontalLayout(0);
		buttonList.addMenuItem(healthRegenUpgrade);
		MenuButton healthRegenButton = new MenuButton(ImageProcessor.getImage("HealthRegenButton"),
				new UpgradeHealthRegenButton());
		Counter healthRegenPrice = new Counter(ImageProcessor.getImage("Coin"), 1);
		healthRegenUpgrade.additem(healthRegenButton);
		healthRegenUpgrade.additem(healthRegenPrice);

		HorizontalLayout healthUpgrade = new HorizontalLayout(0);
		buttonList.addMenuItem(healthUpgrade);
		MenuButton healthUpgradeButton = new MenuButton(ImageProcessor.getImage("HealthButton"),
				new UpgradeHealthButton());

		Counter healthUpgradePrice = new Counter(ImageProcessor.getImage("Coin"), 1);
		healthUpgrade.additem(healthUpgradeButton);
		healthUpgrade.additem(healthUpgradePrice);

		HorizontalLayout manaRegenUpgrade = new HorizontalLayout(0);
		buttonList.addMenuItem(manaRegenUpgrade);
		MenuButton manaRegenButton = new MenuButton(ImageProcessor.getImage("ManaRegenButton"),
				new UpgradeManaRegenButton());
		Counter manaRegenPrice = new Counter(ImageProcessor.getImage("Coin"), 1);
		manaRegenUpgrade.additem(manaRegenButton);
		manaRegenUpgrade.additem(manaRegenPrice);

		HorizontalLayout manaUpgrade = new HorizontalLayout(0);
		buttonList.addMenuItem(manaUpgrade);
		MenuButton manaButton = new MenuButton(ImageProcessor.getImage("ManaButton"), new UpgradeManaButton());
		Counter manaPrice = new Counter(ImageProcessor.getImage("Coin"), 1);
		manaUpgrade.additem(manaButton);
		manaUpgrade.additem(manaPrice);

		addMenuItem(buttonList);
		BaseInventory playerInventory = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		skillPoints = new Counter(x + 30.0f, y + 191.0f, ImageProcessor.getImage("Coin"), false,
				playerInventory.getLevelPoints(), 1f);
	}
}
