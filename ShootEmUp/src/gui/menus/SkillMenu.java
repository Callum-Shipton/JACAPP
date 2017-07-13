package gui.menus;

import components.TypeComponent;
import components.inventory.BaseInventory;
import display.Image;
import display.ImageProcessor;
import gui.Counter;
import gui.CounterButton;
import gui.VerticalLayout;
import gui.buttons.UpgradeHealthButton;
import gui.buttons.UpgradeHealthRegenButton;
import gui.buttons.UpgradeManaButton;
import gui.buttons.UpgradeManaRegenButton;
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
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		skillPoints.update(BI.getLevelPoints());
	}

	@Override
	public void resetMenu() {
		super.resetMenu();
		VerticalLayout buttonList = new VerticalLayout(x + 30, y + 30,
				ImageProcessor.getImage("HealthButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new CounterButton(0, 0, ImageProcessor.getImage("HealthRegenButton"),
				ImageProcessor.getImage("Coin"), 1, 1f, new UpgradeHealthRegenButton()));
		buttonList.addMenuItem(new CounterButton(0, 0, ImageProcessor.getImage("HealthButton"),
				ImageProcessor.getImage("Coin"), 1, 1f, new UpgradeHealthButton()));
		buttonList.addMenuItem(new CounterButton(0, 0, ImageProcessor.getImage("ManaRegenButton"),
				ImageProcessor.getImage("Coin"), 1, 1f, new UpgradeManaRegenButton()));
		buttonList.addMenuItem(new CounterButton(0, 0, ImageProcessor.getImage("ManaButton"),
				ImageProcessor.getImage("Coin"), 1, 1f, new UpgradeManaButton()));
		menuItems.add(buttonList);
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		skillPoints = new Counter(x + 30.0f, y + 191.0f, ImageProcessor.getImage("Coin"), false, BI.getLevelPoints(),
				1f);
	}
}
