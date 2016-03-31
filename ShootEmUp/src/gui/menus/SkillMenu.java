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

public class SkillMenu extends PauseMenu {

	Counter skillPoints;

	public SkillMenu(Image menuImage) {
		super(menuImage);

		ButtonList buttonList = new ButtonList(30, 30, Art.getImage("HealthButton").getHeight() / 2, 20);
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.HEALTH_REGEN, Art.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.HEALTH, Art.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MANA_REGEN, Art.getImage("Coin"), 1, 1f));
		buttonList.addMenuItem(new CounterButton(0, 0, TypeButton.MANA, Art.getImage("Coin"), 1, 1f));
		menuItems.add(buttonList);
		skillPoints = new Counter(30.0f, 191.0f, Art.getImage("Coin"), false,
				((BaseInventory) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))
						.getLevelPoints(),
				1f);
	}

	@Override
	public void update() {
		super.update();
		skillPoints.update(((BaseInventory) ShootEmUp.currentLevel.getPlayer().getComponent(TypeComponent.INVENTORY))
				.getLevelPoints());
	}

	@Override
	public void render() {
		super.render();
		skillPoints.render(Art.stat);
	}
}
