package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.TypeButton;
import main.ShootEmUp;

public class GameOverMenu extends GuiMenu {

	public GameOverMenu(Image menuImage) {
		super(menuImage);
		ShootEmUp.mainMenu = true;
		menuItems.add(ButtonBuilder.buildButton(TypeButton.MAIN_MENU,
				(display.getWidth() / 2) - (Art.getImage("ExitButton").getWidth() / 2),
				(display.getWidth() / 2) - (Art.getImage("ExitButton").getHeight() / 2)));
	}
}
