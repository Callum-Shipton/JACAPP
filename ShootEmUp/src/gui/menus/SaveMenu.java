package gui.menus;

import gui.ButtonBuilder;
import gui.TypeButton;
import display.Image;

public class SaveMenu extends PauseMenu {
	
    public SaveMenu(Image menuImage) {
        super(menuImage);
        menuItems.add(ButtonBuilder.buildButton(TypeButton.SAVE_GAME, 30, 30));
    }
}
