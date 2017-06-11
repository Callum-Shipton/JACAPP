package gui.menus;

import display.Art;
import display.Image;
import gui.MenuButton;
import gui.TypeButton;
import gui.VerticalLayout;
import main.ShootEmUp;

public class LevelSelectMenu extends GuiMenu {

	public LevelSelectMenu(Image menuImage) {
		super(menuImage);

		VerticalLayout buttonList = new VerticalLayout(
				(this.display.getWidth() / 2) - (Art.getImage("Level1Button").getWidth() / 2), 150,
				Art.getImage("Level1Button").getHeight() / 2, 20);
		if (ShootEmUp.getSave() == null) {
			buttonList.addMenuItem(new MenuButton(TypeButton.LEVEL1, Art.getImage("Level1Button"), 0, 0));
		} else {
			switch (ShootEmUp.getSave().getLevel()) {
			case 3:
				buttonList.addMenuItem(new MenuButton(TypeButton.LEVEL3, Art.getImage("Level3Button"), 0, 0));
			case 2:
				buttonList.addMenuItem(new MenuButton(TypeButton.LEVEL2, Art.getImage("Level2Button"), 0, 0));
			case 1:
				buttonList.addMenuItem(new MenuButton(TypeButton.LEVEL1, Art.getImage("Level1Button"), 0, 0));
			}
		}
		buttonList.addMenuItem(new MenuButton(TypeButton.BACK, Art.getImage("BackButton"), 0, 0));

		this.menuItems.add(buttonList);
	}
}
