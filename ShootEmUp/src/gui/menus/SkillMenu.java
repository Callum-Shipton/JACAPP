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
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.HEALTH_REGEN,
				ImageProcessor.getImage("HealthRegenButton"), ImageProcessor.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.HEALTH, ImageProcessor.getImage("HealthButton"),
				ImageProcessor.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MANA_REGEN,
				ImageProcessor.getImage("ManaRegenButton"), ImageProcessor.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MANA, ImageProcessor.getImage("ManaButton"),
				ImageProcessor.getImage("Coin"), 1, 1f));
		menuItems.add(buttonList);
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		skillPoints = new Counter(x + 30.0f, y + 191.0f, ImageProcessor.getImage("Coin"), false, BI.getLevelPoints(),
				1f);
	}
}
