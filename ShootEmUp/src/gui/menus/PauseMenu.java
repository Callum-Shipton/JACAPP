package gui.menus;

import display.Image;
import gui.ButtonBuilder;
import gui.TypeButton;
import main.ShootEmUp;

public abstract class PauseMenu extends GuiMenu {
	public PauseMenu(Image MenuImage){
		super(MenuImage);
		addButton(ButtonBuilder.buildButton(TypeButton.RESUME, 30, ShootEmUp.height - 64));
        addButton(ButtonBuilder.buildButton(TypeButton.MAIN_MENU, 30, ShootEmUp.height - 94));
        addButton(ButtonBuilder.buildButton(TypeButton.INVENTORY, 922, 0));
        addButton(ButtonBuilder.buildButton(TypeButton.SKILLS, 922, 204));
        addButton(ButtonBuilder.buildButton(TypeButton.UPGRADES, 922, 102));
        addButton(ButtonBuilder.buildButton(TypeButton.MAP, 922, 306));
        addButton(ButtonBuilder.buildButton(TypeButton.SAVE, 922, 408));
	}
}
