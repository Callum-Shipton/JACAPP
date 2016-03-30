package gui.menus;

import gui.ButtonBuilder;
import gui.TypeButton;
import main.ShootEmUp;

import display.Art;
import display.Image;

public class GameOverMenu extends GuiMenu{
	
    public GameOverMenu(Image menuImage) {
        super(menuImage);
        ShootEmUp.mainMenu = true;
        menuItems.add(ButtonBuilder.buildButton(TypeButton.MAIN_MENU, (ShootEmUp.width / 2) - (Art.exitButton.getWidth() / 2), (ShootEmUp.height / 2) - (Art.exitButton.getHeight() / 2)));
    }
}
