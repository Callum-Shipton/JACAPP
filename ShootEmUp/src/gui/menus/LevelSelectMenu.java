package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.VerticalLayout;
import gui.TypeButton;
import main.ShootEmUp;

public class LevelSelectMenu extends GuiMenu {

	public LevelSelectMenu(Image menuImage) {
		super(menuImage);

		VerticalLayout buttonList = new VerticalLayout((display.getWidth() / 2) - (Art.getImage("Level1Button").getWidth() / 2),
				150, Art.getImage("Level1Button").getHeight() / 2, 20);
		if (ShootEmUp.save == null) {
			buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.LEVEL1, 0, 0));
		} else {
			switch (ShootEmUp.save.getLevel()) {
				case 3:
					buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.LEVEL3, 0, 0));
				case 2:
					buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.LEVEL2, 0, 0));
				case 1:
					buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.LEVEL1, 0, 0));
			}
		}
		buttonList.addMenuItem(ButtonBuilder.buildButton(TypeButton.BACK, 0, 0));

		menuItems.add(buttonList);
	}
}
