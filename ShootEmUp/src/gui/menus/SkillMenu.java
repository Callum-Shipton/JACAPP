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

public class SkillMenu extends PauseMenu {

	Counter skillPoints;

	public SkillMenu(Image menuImage) {
		super(menuImage);

		VerticalLayout buttonList = new VerticalLayout(30, 30, Art.getImage("HealthButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.HEALTH_REGEN, Art.getImage("HealthRegenButton"),
				Art.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(
				new CounterButton(0, 0, TypeButton.HEALTH, Art.getImage("HealthButton"), Art.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MANA_REGEN, Art.getImage("ManaRegenButton"),
				Art.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(
				new CounterButton(0, 0, TypeButton.MANA, Art.getImage("ManaButton"), Art.getImage("Coin"), 1, 1f));
		this.menuItems.add(buttonList);
		BaseInventory BI = Loop.getPlayer().getComponent(TypeComponent.INVENTORY);
		this.skillPoints = new Counter(30.0f, 191.0f, Art.getImage("Coin"), false, BI.getLevelPoints(), 1f);
	}

	@Override
	public void render() {
		super.render();
		this.skillPoints.render(Art.stat);
	}

	@Override
	public void update() {
		super.update();
		BaseInventory BI = Loop.getPlayer().getComponent(TypeComponent.INVENTORY);
		this.skillPoints.update(BI.getLevelPoints());
	}
}
