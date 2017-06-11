package gui.menus;

import display.Art;
import display.Image;
import gui.MenuButton;
import gui.TypeButton;

public class SaveMenu extends PauseMenu {

	public SaveMenu(Image menuImage) {
		super(menuImage);
		this.menuItems.add(new MenuButton(TypeButton.SAVE_GAME, Art.getImage("SaveGameButton"), 30, 30));
	}
}
