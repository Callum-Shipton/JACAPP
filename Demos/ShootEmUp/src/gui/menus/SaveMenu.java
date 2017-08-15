package gui.menus;

import display.Art;
import display.Image;
import display.ImageProcessor;
import gui.Button;
import gui.buttons.SaveButton;

public class SaveMenu extends PauseMenu {

	public SaveMenu(Image menuImage) {
		super(menuImage);
	}

	@Override
	public void resetMenu() {
		super.resetMenu();

		addMenuItem(new Button(Art.getImage("SaveGameButton"), x + 30, y + 30, new SaveButton()));
	}
}
