package gui.menus;

import gui.Button;
import gui.ButtonType;
import display.Art;
import display.Image;

public class MagicMenu extends GuiMenu {

    public MagicMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.RESUME, Art.backButton, 30, 30));
        addButton(new Button(ButtonType.MAIN_MENU, Art.exitButton, 30, 64));
        addButton(new Button(ButtonType.INVENTORY, Art.invButton, 922, 0));
        addButton(new Button(ButtonType.SKILLS, Art.skillButton, 922, 204));
        addButton(new Button(ButtonType.MAP, Art.mapButton, 922, 306));
        addButton(new Button(ButtonType.SAVE, Art.saveButton, 922, 408));
        addButton(new Button(ButtonType.INVENTORY_UPGRADE, Art.inventoryButton,30, 98));
		addButton(new Button(ButtonType.POTIONS_UPGRADE, Art.potionsButton,30, 132));
    }
}
