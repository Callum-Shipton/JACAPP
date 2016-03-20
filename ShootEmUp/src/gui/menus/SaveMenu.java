package gui.menus;

import gui.Button;
import gui.ButtonType;
import display.Art;
import display.Image;

public class SaveMenu extends GuiMenu {
	
    public SaveMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.RESUME, Art.backButton, 30, 30, 128,24));
        addButton(new Button(ButtonType.MAIN_MENU, Art.exitButton, 30, 64, 128,24));
        addButton(new Button(ButtonType.INVENTORY, Art.invButton, 922, 0, 101, 102));
        addButton(new Button(ButtonType.SKILLS, Art.skillButton, 922, 204, 101, 102));
        addButton(new Button(ButtonType.MAGIC, Art.magicButton, 922, 102, 101, 102));
        addButton(new Button(ButtonType.MAP, Art.mapButton, 922, 306, 101, 102));
        addButton(new Button(ButtonType.SAVE_GAME, Art.loadGameButton,30, 98, 128, 24));
    }
}
