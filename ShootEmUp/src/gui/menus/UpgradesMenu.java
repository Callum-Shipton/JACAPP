package gui.menus;

import gui.Button;
import gui.ButtonType;
import display.Art;
import display.Image;

public class UpgradesMenu extends PauseMenu {

    public UpgradesMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.INVENTORY_UPGRADE, Art.inventoryButton,30, 98));
		addButton(new Button(ButtonType.POTIONS_UPGRADE, Art.potionsButton,30, 132));
    }
}
