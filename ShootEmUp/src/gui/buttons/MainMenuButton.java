package gui.buttons;

import display.ImageProcessor;
import gui.ButtonAction;
import gui.menus.MainMenu;
import main.ShootEmUp;

public class MainMenuButton implements ButtonAction {
	@Override
	public void click() {
		ShootEmUp.getMenuSystem().setMainMenu(true);
		ShootEmUp.getMenuSystem().clearMenus();
		ShootEmUp.getMenuSystem().addMenu(new MainMenu(ImageProcessor.getImage("MainMenuScreen")));
	}
}
