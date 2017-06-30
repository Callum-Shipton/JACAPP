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

		VerticalLayout buttonList = new VerticalLayout(30, 30, ImageProcessor.getImage("HealthButton").getHeight() / 2,
				20);
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.HEALTH_REGEN,
				ImageProcessor.getImage("HealthRegenButton"), ImageProcessor.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.HEALTH, ImageProcessor.getImage("HealthButton"),
				ImageProcessor.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MANA_REGEN,
				ImageProcessor.getImage("ManaRegenButton"), ImageProcessor.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MANA, ImageProcessor.getImage("ManaButton"),
				ImageProcessor.getImage("Coin"), 1, 1f));
		this.menuItems.add(buttonList);
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		this.skillPoints = new Counter(30.0f, 191.0f, ImageProcessor.getImage("Coin"), false, BI.getLevelPoints(), 1f);
	}

	@Override
	public void render() {
		super.render();
		this.skillPoints.render(ImageProcessor.stat);
	}

	@Override
	public void update() {
		super.update();
		BaseInventory BI = ShootEmUp.getGame().getPlayer().getComponent(TypeComponent.INVENTORY);
		this.skillPoints.update(BI.getLevelPoints());
	}
}
