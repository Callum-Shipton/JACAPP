package gui.menus;

import display.Art;
import display.Image;
import gui.MenuButton;
import gui.TypeButton;
import main.Loop;

public class GameOverMenu extends GuiMenu {

	public GameOverMenu(Image menuImage) {
		super(menuImage);
		Loop.getMenuSystem().setMainMenu(true);
		Image buttonImage = Art.getImage("ExitButton");
		this.menuItems.add(
				new MenuButton(TypeButton.MAIN_MENU, buttonImage, (this.display.getWidth() / 2) - (buttonImage.getWidth() / 2),
						(this.display.getWidth() / 2) - (buttonImage.getHeight() / 2)));
	}
}
