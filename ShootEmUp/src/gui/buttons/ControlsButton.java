package gui.buttons;

import display.ImageProcessor;
import gui.ButtonAction;
import gui.menus.ControlsMenu;
import main.ShootEmUp;

public class ControlsButton implements ButtonAction {
	@Override
	public void click() {
		ShootEmUp.getMenuSystem().addMenu(new ControlsMenu(ImageProcessor.getImage("MainMenuScreen")));
	}
}
