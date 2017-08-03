package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.Button;
import gui.buttons.MainMenuButton;
import main.ShootEmUp;

public class GameOverMenu extends GuiMenu {

	public GameOverMenu(Image menuImage) {
		super(menuImage, true);
		ShootEmUp.getMenuSystem().setMainMenu(true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		clearMenu();
		Image buttonImage = ImageProcessor.getImage("ExitButton");
		addMenuItem(new Button(buttonImage, (display.getWidth() / 2) - (buttonImage.getWidth() / 2),
				(display.getWidth() / 2) - (buttonImage.getHeight() / 2), new MainMenuButton()));
	}
}
