package gui.menus;

import gui.Button;
import gui.ButtonType;
import display.Art;
import display.Image;

public class SaveMenu extends PauseMenu {
	
    public SaveMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.SAVE_GAME, Art.saveGameButton,30, 98));
    }
}
