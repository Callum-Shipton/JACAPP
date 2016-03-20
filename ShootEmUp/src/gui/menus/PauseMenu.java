package gui.menus;

import display.Art;
import display.Image;
import gui.Button;
import gui.ButtonType;

public abstract class PauseMenu extends GuiMenu {
	public PauseMenu(Image MenuImage){
		super(MenuImage);
		addButton(new Button(ButtonType.RESUME, Art.backButton, 30, 30));
        addButton(new Button(ButtonType.MAIN_MENU, Art.exitButton, 30, 64));
        addButton(new Button(ButtonType.INVENTORY, Art.invButton, 922, 0));
        addButton(new Button(ButtonType.SKILLS, Art.skillButton, 922, 204));
        addButton(new Button(ButtonType.UPGRADES, Art.upgradesButton, 922, 102));
        addButton(new Button(ButtonType.MAP, Art.mapButton, 922, 306));
        addButton(new Button(ButtonType.SAVE, Art.saveButton, 922, 408));
	}
}
