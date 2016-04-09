package gui.menus;

import display.Image;
import gui.ButtonBuilder;
import gui.TypeButton;

public class SaveMenu extends PauseMenu {

	public SaveMenu(Image menuImage) {
		super(menuImage);
		this.menuItems.add(ButtonBuilder.buildButton(TypeButton.SAVE_GAME, 30, 30));
	}
}
