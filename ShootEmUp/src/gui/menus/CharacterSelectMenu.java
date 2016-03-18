package gui.menus;

import gui.Button;
import gui.ButtonType;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class CharacterSelectMenu extends GuiMenu {
	
	static int selectedItem = 0;
	public static boolean saved;

    public CharacterSelectMenu(Image menuImage) {
        super(menuImage);
        selectedItem = 0;
        addButton(new Button(ButtonType.WARRIOR, Art.warriorButton, (ShootEmUp.width / 2) - (Art.warriorButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.warriorButton.getHeight() * 2), 128,24));
        addButton(new Button(ButtonType.ARCHER, Art.archerButton, (ShootEmUp.width / 2) - (Art.archerButton.getWidth() / 2), (ShootEmUp.height / 2) - Art.archerButton.getHeight(), 128,24));
        addButton(new Button(ButtonType.MAGE, Art.mageButton, (ShootEmUp.width / 2) - (Art.mageButton.getWidth() / 2), (ShootEmUp.height / 2), 128,24));
        addButton(new Button(ButtonType.BACK, Art.backButton, (ShootEmUp.width / 2) - (Art.backButton.getWidth() / 2), (ShootEmUp.height / 2) + Art.backButton.getHeight(), 128,24));
    }
}
