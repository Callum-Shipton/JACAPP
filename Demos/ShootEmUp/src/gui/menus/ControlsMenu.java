package gui.menus;

import display.ArtLoader;
import display.Image;
import gui.GuiMenu;
import gui.Button;
import gui.buttons.BackButton;

public class ControlsMenu extends GuiMenu {

	public ControlsMenu() {
		super(ArtLoader.getImage("MainMenuScreen"), true);
		resetMenu();
	}

	@Override
	public void resetMenu() {
		clearMenu();
		Image button = ArtLoader.getImage("BackButton");
		addMenuItem(new Button(button, (display.getWidth() / 2) - (button.getWidth() / 2),
				(display.getHeight() / 2) - (button.getHeight() / 2), new BackButton()));
	}
}
