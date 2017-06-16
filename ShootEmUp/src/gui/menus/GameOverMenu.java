package gui.menus;

import display.Art;
import display.Image;
import gui.MenuButton;
import gui.TypeButton;
import main.ShootEmUp;

public class GameOverMenu extends GuiMenu {

	public GameOverMenu(Image menuImage) {
		super(menuImage);
		ShootEmUp.getMenuSystem().setMainMenu(true);
		Image buttonImage = Art.getImage("ExitButton");
		this.menuItems.add(new MenuButton(TypeButton.MAIN_MENU, buttonImage,
				(this.display.getWidth() / 2) - (buttonImage.getWidth() / 2),
				(this.display.getWidth() / 2) - (buttonImage.getHeight() / 2)));
	}
}
