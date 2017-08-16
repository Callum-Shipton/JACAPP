package gui.menus;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.ArtLoader;
import display.Image;
import display.BaseRenderSystem;
import gui.Counter;
import gui.Button;
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
		skillPoints.render(BaseRenderSystem.stat);
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
		Button healthRegenButton = new Button(ArtLoader.getImage("HealthRegenButton"),
				new UpgradeHealthRegenButton());
		Counter healthRegenPrice = new Counter(ArtLoader.getImage("Coin"), 1);
		healthRegenUpgrade.additem(healthRegenButton);
		healthRegenUpgrade.additem(healthRegenPrice);

		HorizontalLayout healthUpgrade = new HorizontalLayout(0);
		buttonList.addMenuItem(healthUpgrade);
		Button healthUpgradeButton = new Button(ArtLoader.getImage("HealthButton"),
				new UpgradeHealthButton());

		Counter healthUpgradePrice = new Counter(ArtLoader.getImage("Coin"), 1);
		healthUpgrade.additem(healthUpgradeButton);
		healthUpgrade.additem(healthUpgradePrice);

		HorizontalLayout manaRegenUpgrade = new HorizontalLayout(0);
		buttonList.addMenuItem(manaRegenUpgrade);
		Button manaRegenButton = new Button(ArtLoader.getImage("ManaRegenButton"),
				new UpgradeManaRegenButton());
		Counter manaRegenPrice = new Counter(ArtLoader.getImage("Coin"), 1);
		manaRegenUpgrade.additem(manaRegenButton);
		manaRegenUpgrade.additem(manaRegenPrice);

		HorizontalLayout manaUpgrade = new HorizontalLayout(0);
		buttonList.addMenuItem(manaUpgrade);
		Button manaButton = new Button(ArtLoader.getImage("ManaButton"), new UpgradeManaButton());
		Counter manaPrice = new Counter(ArtLoader.getImage("Coin"), 1);
		manaUpgrade.additem(manaButton);
		manaUpgrade.additem(manaPrice);

		addMenuItem(buttonList);
		BaseInventory playerInventory = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		skillPoints = new Counter(x + 30.0f, y + 191.0f, ArtLoader.getImage("Coin"), false,
				playerInventory.getLevelPoints(), 1f);
	}
}
