package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.TypeButton;
import main.ShootEmUp;

public class GameOverMenu extends GuiMenu {

	public GameOverMenu(Image menuImage) {
		super(menuImage);
		ShootEmUp.getMenuSystem().setMainMenu(true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		menuItems.clear();
		Image buttonImage = ImageProcessor.getImage("ExitButton");
		menuItems.add(new MenuButton(TypeButton.MAIN_MENU, buttonImage,
				(display.getWidth() / 2) - (buttonImage.getWidth() / 2),
				(display.getWidth() / 2) - (buttonImage.getHeight() / 2)));
	}
}
