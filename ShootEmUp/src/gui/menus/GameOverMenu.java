package gui.menus;

import display.Art;
import display.Image;
import gui.ButtonBuilder;
import gui.TypeButton;
import main.ShootEmUp;

public class GameOverMenu extends GuiMenu {

	public GameOverMenu(Image menuImage) {
		super(menuImage);
		ShootEmUp.getMenuSystem().setMainMenu(true);
		this.menuItems.add(ButtonBuilder.buildButton(TypeButton.MAIN_MENU,
				(this.display.getWidth() / 2) - (Art.getImage("ExitButton").getWidth() / 2),
				(this.display.getWidth() / 2) - (Art.getImage("ExitButton").getHeight() / 2)));
	}
}
