package gui.menus;

import gui.Button;
import gui.ButtonType;
import display.Art;
import display.Image;

public class MapMenu extends GuiMenu {

    public MapMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.RESUME, Art.backButton, 30, 30));
        addButton(new Button(ButtonType.MAIN_MENU, Art.exitButton, 30, 64));
        addButton(new Button(ButtonType.INVENTORY, Art.invButton, 922, 0));
        addButton(new Button(ButtonType.SKILLS, Art.skillButton, 922, 204));
        addButton(new Button(ButtonType.MAGIC, Art.magicButton, 922, 102));
        addButton(new Button(ButtonType.SAVE, Art.saveButton, 922, 408));
    }
}
