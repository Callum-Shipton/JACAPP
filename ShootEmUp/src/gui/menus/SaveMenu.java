package gui.menus;

import display.Image;
import display.ImageProcessor;
import gui.MenuButton;
import gui.TypeButton;

public class SaveMenu extends PauseMenu {

	public SaveMenu(Image menuImage) {
		super(menuImage);
	}

	@Override
	public void resetMenu() {
		super.resetMenu();

		menuItems.add(new MenuButton(TypeButton.SAVE_GAME, ImageProcessor.getImage("SaveGameButton"), x + 30, y + 30));
	}
}
