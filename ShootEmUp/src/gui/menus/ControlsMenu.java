package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.GuiMenu;
import gui.MenuButton;
import gui.buttons.BackButton;

public class ControlsMenu extends GuiMenu {

	public ControlsMenu() {
		super(ImageProcessor.getImage("MainMenuScreen"), true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		clearMenu();
		Image button = ImageProcessor.getImage("BackButton");
		addMenuItem(new MenuButton(button, (display.getWidth() / 2) - (button.getWidth() / 2),
				(display.getHeight() / 2) - (button.getHeight() / 2), new BackButton()));
	}
}
