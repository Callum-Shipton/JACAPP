package gui.menus;

import gui.Button;
import gui.ButtonType;
import display.Art;
import display.Image;

public class SkillMenu extends PauseMenu {

    public SkillMenu(Image menuImage) {
        super(menuImage);
		addButton(new Button(ButtonType.HEALTH_REGEN, Art.healthRegenButton,30, 98));
		addButton(new Button(ButtonType.HEALTH, Art.healthButton,30, 132));
		addButton(new Button(ButtonType.MANA_REGEN, Art.manaRegenButton,30, 166));
		addButton(new Button(ButtonType.MANA, Art.manaButton,30, 200));
    }
}
