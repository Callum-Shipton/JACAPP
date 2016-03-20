package gui.menus;

import gui.Button;
import gui.ButtonType;
import display.Art;
import display.Image;

public class SkillMenu extends GuiMenu {

    public SkillMenu(Image menuImage) {
        super(menuImage);
        addButton(new Button(ButtonType.RESUME, Art.backButton, 30, 30));
        addButton(new Button(ButtonType.MAIN_MENU, Art.exitButton, 30, 64));
        addButton(new Button(ButtonType.INVENTORY, Art.invButton, 922, 0));
        addButton(new Button(ButtonType.MAGIC, Art.magicButton, 922, 102));
        addButton(new Button(ButtonType.MAP, Art.mapButton, 922, 306));
        addButton(new Button(ButtonType.SAVE, Art.saveButton, 922, 408));
		addButton(new Button(ButtonType.HEALTH_REGEN, Art.healthRegenButton,30, 98));
		addButton(new Button(ButtonType.HEALTH, Art.healthButton,30, 132));
		addButton(new Button(ButtonType.MANA_REGEN, Art.manaRegenButton,30, 166));
		addButton(new Button(ButtonType.MANA, Art.manaButton,30, 200));
    }
}
